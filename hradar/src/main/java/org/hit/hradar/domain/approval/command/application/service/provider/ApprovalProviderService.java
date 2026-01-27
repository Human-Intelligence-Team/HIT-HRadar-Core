package org.hit.hradar.domain.approval.command.application.service.provider;

import java.util.List;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.approval.ApprovalErrorCode;
import org.hit.hradar.domain.approval.command.application.dto.request.ApprovalCreateRequest;
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

  //결재 문서 저장 진입점
  //DRAFT : 문서 내용/결재선/참조자 수정 가능
  //SUBMIT: 상태 전이만 수행 (데이터 변경 X)
  public Long save(
      Long docId,
      Long writerId,
      Long companyId,
      ApprovalCreateRequest request,
      ApprovalSaveMode mode
  ) {

    // 최초 임시저장 (docId == null)
    if (docId == null) {

      if (mode != ApprovalSaveMode.DRAFT) {
        throw new BusinessException(
            ApprovalErrorCode.CANNOT_SUBMIT_NOT_DRAFT
        );
      }

      if (request == null) {
        throw new IllegalArgumentException("임시저장은 request가 필수입니다.");
      }

      ApprovalDocument document = ApprovalDocument.createDraft(
          writerId,
          companyId,
          request.getDocType(),
          request.getTitle(),
          request.getContent()
      );
      approvalDocumentJpaRepository.save(document);

      // 결재선 생성
      ApprovalLine line = ApprovalLine.create(document.getDocId());
      approvalLineJpaRepository.save(line);

      // 결재 단계 생성
      saveSteps(line.getLineId(), request.getApproverIds());

      // 참조자 생성
      saveReferences(document.getDocId(), request.getReferenceIds());

      return document.getDocId();
    }

    // 기존 문서 조회
    ApprovalDocument document = approvalDocumentJpaRepository.findById(docId)
        .orElseThrow(() -> new BusinessException(
            ApprovalErrorCode.DOCUMENT_NOT_FOUND
        ));

    if (!document.isOwner(writerId)) {
      throw new BusinessException(
          ApprovalErrorCode.NOT_ALLOWED_EDIT
      );
    }

    // 임시저장 수정
    if (mode == ApprovalSaveMode.DRAFT) {

      if (request == null) {
        throw new IllegalArgumentException("임시저장 수정은 request가 필수입니다.");
      }

      if (!document.isDraft()) {
        throw new BusinessException(
            ApprovalErrorCode.CANNOT_SUBMIT_NOT_DRAFT
        );
      }

      document.update(
          request.getTitle(),
          request.getContent(),
          request.getDocType()
      );

      ApprovalLine line = approvalLineJpaRepository.findByDocId(docId)
          .orElseThrow(() -> new BusinessException(
              ApprovalErrorCode.LINE_NOT_FOUND
          ));

      // 기존 결재 단계 / 참조자 제거
      approvalLineStepJpaRepository.deleteByLineId(line.getLineId());
      approvalReferenceJpaRepository.deleteByDocId(docId);

      // 새 결재 단계 / 참조자 등록
      saveSteps(line.getLineId(), request.getApproverIds());
      saveReferences(docId, request.getReferenceIds());

      return document.getDocId();
    }

    //상신 처리 (핵심)
    if (mode == ApprovalSaveMode.SUBMIT) {

      if (!document.isDraft()) {
        throw new BusinessException(
            ApprovalErrorCode.CANNOT_SUBMIT_NOT_DRAFT
        );
      }

      document.submit(writerId);

      ApprovalLine line = approvalLineJpaRepository.findByDocId(docId)
          .orElseThrow(() -> new BusinessException(
              ApprovalErrorCode.LINE_NOT_FOUND
          ));

      ApprovalLineStep firstStep =
          approvalLineStepJpaRepository
              .findFirstByLineIdAndApprovalStepStatusOrderByStepOrderAsc(
                  line.getLineId(),
                  ApprovalStepStatus.WAITING
              )
              .orElseThrow(() -> new BusinessException(
                  ApprovalErrorCode.NO_PENDING_STEP
              ));

      firstStep.changeToPending();

      approvalHistoryJpaRepository.save(
          ApprovalHistory.submit(docId, writerId)
      );

      return docId;
    }

    throw new IllegalArgumentException("지원하지 않는 ApprovalSaveMode 입니다.");
  }

  // 결재 단계 생성
  private void saveSteps(Long lineId, List<Long> approverIds) {

    if (approverIds == null || approverIds.isEmpty()) {
      throw new BusinessException(
          ApprovalErrorCode.NO_PENDING_STEP
      );
    }

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

  // 참조자 생성
  private void saveReferences(Long docId, List<Long> referenceIds) {

    if (referenceIds == null || referenceIds.isEmpty()) {
      return;
    }

    approvalReferenceJpaRepository.saveAll(
        ApprovalReference.createAll(docId, referenceIds)
    );
  }

}
