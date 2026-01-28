package org.hit.hradar.domain.approval.command.application.service.provider;

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

    if (docId == null) {

      if (mode != ApprovalSaveMode.DRAFT) {
        throw new BusinessException(ApprovalErrorCode.CANNOT_SUBMIT_NOT_DRAFT);
      }

      validateDraftRequest(request);

      ApprovalDocument document = ApprovalDocument.createDraft(
          writerId,
          companyId,
          request.getDocType(),
          request.getTitle(),
          request.getContent()
      );
      approvalDocumentJpaRepository.save(document);

      savePayload(document.getDocId(), request.getPayload());

      ApprovalLine line = ApprovalLine.create(document.getDocId());
      approvalLineJpaRepository.save(line);

      saveSteps(line.getLineId(), request.getApproverIds());
      saveReferences(document.getDocId(), request.getReferenceIds());

      return document.getDocId();
    }

    ApprovalDocument document =
        approvalDocumentJpaRepository.findById(docId)
            .orElseThrow(() ->
                new BusinessException(ApprovalErrorCode.DOCUMENT_NOT_FOUND));

    if (!document.isOwner(writerId)) {
      throw new BusinessException(ApprovalErrorCode.NOT_ALLOWED_EDIT);
    }

    if (mode == ApprovalSaveMode.DRAFT) {

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

      return docId;
    }

    if (mode == ApprovalSaveMode.SUBMIT) {

      if (!document.isDraft()) {
        throw new BusinessException(ApprovalErrorCode.ALREADY_SUBMITTED);
      }

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

      return docId;
    }

    throw new BusinessException(ApprovalErrorCode.INVALID_SAVE_MODE);
  }

  /* ===== private ===== */

  private void validateDraftRequest(ApprovalDraftCreateRequest request) {
    if (request == null) {
      throw new IllegalArgumentException("request는 필수입니다.");
    }
    if (request.getDocType() == null || request.getDocType().isBlank()) {
      throw new BusinessException(ApprovalErrorCode.INVALID_DOC_TYPE_FORMAT);
    }
    if (request.getApproverIds() == null || request.getApproverIds().isEmpty()) {
      throw new BusinessException(ApprovalErrorCode.APPROVER_REQUIRED);
    }
  }

  private void savePayload(Long docId, Object payload) {
    try {
      String json = objectMapper.writeValueAsString(payload);
      approvalPayloadJpaRepository.save(
          ApprovalPayload.create(docId, json)
      );
    } catch (Exception e) {
      throw new BusinessException(ApprovalErrorCode.DOMAIN_PAYLOAD_INVALID);
    }
  }

  private void updatePayload(Long docId, Object payload) {
    try {
      String json = objectMapper.writeValueAsString(payload);
      approvalPayloadJpaRepository.findByDocId(docId)
          .ifPresent(p -> p.update(json));
    } catch (Exception e) {
      throw new BusinessException(ApprovalErrorCode.DOMAIN_PAYLOAD_INVALID);
    }
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
}
