package org.hit.hradar.domain.approval.command.application.service.provider;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.approval.ApprovalErrorCode;
import org.hit.hradar.domain.approval.command.application.dto.request.ApprovalDraftCreateRequest;
import org.hit.hradar.domain.approval.command.domain.aggregate.*;
import org.hit.hradar.domain.approval.command.infrastructure.*;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ApprovalProviderService {

  private final ApprovalDocumentJpaRepository approvalDocumentJpaRepository;
  private final ApprovalLineJpaRepository approvalLineJpaRepository;
  private final ApprovalLineStepJpaRepository approvalLineStepJpaRepository;
  private final ApprovalHistoryJpaRepository approvalHistoryJpaRepository;
  private final ApprovalReferenceJpaRepository approvalReferenceJpaRepository;
  private final ApprovalPayloadJpaRepository approvalPayloadJpaRepository;

  private final ObjectMapper objectMapper;

  public Long save(
      Long docId,
      Long writerId,
      Long companyId,
      ApprovalDraftCreateRequest request,
      ApprovalSaveMode mode
  ) {

    ApprovalDocument document;

    // 1. 문서 생성 또는 조회
    if (docId == null) {
      // 신규 생성
      validateDraftRequest(request);

      document = ApprovalDocument.createDraft(
          writerId,
          companyId,
          request.getDocType(),
          request.getTitle(),
          request.getContent()
      );
      approvalDocumentJpaRepository.save(document);
      docId = document.getDocId();

      savePayload(docId, request.getPayload());

      ApprovalLine line = ApprovalLine.create(docId);
      approvalLineJpaRepository.save(line);

      saveSteps(line.getLineId(), request.getApproverIds());
      saveReferences(docId, request.getReferenceIds());

    } else {
      // 기존 문서 조회 및 수정
      document = approvalDocumentJpaRepository.findById(docId)
          .orElseThrow(() ->
              new BusinessException(ApprovalErrorCode.DOCUMENT_NOT_FOUND));

      if (!document.isOwner(writerId)) {
        throw new BusinessException(ApprovalErrorCode.NOT_ALLOWED_EDIT);
      }

      if (!document.isDraft()) {
        throw new BusinessException(ApprovalErrorCode.CANNOT_EDIT_AFTER_SUBMIT);
      }

      validateDraftRequest(request);

      document.update(
          request.getTitle(),
          request.getContent(),
          request.getDocType()
      );

      updatePayload(docId, request.getPayload());

      ApprovalLine line =
          approvalLineJpaRepository.findByDocId(docId)
              .orElseThrow(() ->
                  new BusinessException(ApprovalErrorCode.LINE_NOT_FOUND));

      approvalLineStepJpaRepository.deleteByLineId(line.getLineId());
      approvalReferenceJpaRepository.deleteByDocId(docId);

      saveSteps(line.getLineId(), request.getApproverIds());
      saveReferences(docId, request.getReferenceIds());
    }

    // 2. 모드별 처리 (DRAFT는 이미 저장되었으므로 통과, SUBMIT은 상신 처리)
    if (mode == ApprovalSaveMode.SUBMIT) {
      
      // 이미 위에서 isDraft 체크나 신규 생성을 했으므로 바로 상신 로직 진행
      document.submit(writerId);

      ApprovalLine line =
          approvalLineJpaRepository.findByDocId(docId)
              .orElseThrow(() ->
                  new BusinessException(ApprovalErrorCode.LINE_NOT_FOUND));

      ApprovalLineStep firstStep =
          approvalLineStepJpaRepository
              .findFirstByLineIdAndApprovalStepStatusOrderByStepOrderAsc(
                  line.getLineId(),
                  ApprovalStepStatus.WAITING
              )
              .orElseThrow(() ->
                  new BusinessException(ApprovalErrorCode.NO_PENDING_STEP));

      firstStep.changeToPending();

      approvalHistoryJpaRepository.save(
          ApprovalHistory.submit(docId, writerId)
      );
    }
    
    return docId;
  }

  /* ===== private ===== */

  private void validateDraftRequest(ApprovalDraftCreateRequest request) {
    if (request == null) {
      throw new BusinessException(ApprovalErrorCode.INVALID_REQUEST);
    }
    if (request.getDocType() == null || request.getDocType().isBlank()) {
      throw new BusinessException(ApprovalErrorCode.INVALID_DOC_TYPE_FORMAT);
    }
    if (request.getApproverIds() == null || request.getApproverIds().isEmpty()) {
      throw new BusinessException(ApprovalErrorCode.APPROVER_REQUIRED);
    }
  }

  private void savePayload(Long docId, JsonNode payload) {
    // 일반 결재 문서의 경우 payload가 없을 수 있음 (null 또는 빈 객체)
    if (payload == null || payload.isNull() || payload.isEmpty()) {
      // payload가 없는 경우 저장하지 않음 (선택적)
      return;
    }

    String json = writeJson(payload);
    approvalPayloadJpaRepository.save(
        ApprovalPayload.create(docId, json)
    );
  }

  private void updatePayload(Long docId, JsonNode payload) {
    // 일반 결재 문서의 경우 payload가 없을 수 있음
    if (payload == null || payload.isNull() || payload.isEmpty()) {
      // payload가 없는 경우 업데이트하지 않음
      return;
    }

    String json = writeJson(payload);
    approvalPayloadJpaRepository.findByDocId(docId)
        .ifPresent(p -> p.update(json));
  }

  private void saveSteps(Long lineId, List<Long> approverIds) {
    List<ApprovalLineStep> steps =
        IntStream.range(0, approverIds.size())
            .mapToObj(i ->
                ApprovalLineStep.create(
                    lineId,
                    i + 1,
                    approverIds.get(i)
                )
            )
            .toList();
    approvalLineStepJpaRepository.saveAll(steps);
  }

  private void saveReferences(Long docId, List<Long> referenceIds) {
    if (referenceIds == null || referenceIds.isEmpty()) return;
    approvalReferenceJpaRepository.saveAll(
        ApprovalReference.createAll(docId, referenceIds)
    );
  }

  private String writeJson(JsonNode payload) {
    try {
      return payload.toString();
    } catch (Exception e) {
      throw new BusinessException(ApprovalErrorCode.DOMAIN_PAYLOAD_INVALID);
    }
  }
}
