package org.hit.hradar.domain.approval.command.application.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.approval.ApprovalErrorCode;
import org.hit.hradar.domain.approval.command.domain.aggregate.*;
import org.hit.hradar.domain.approval.command.domain.infrastructure.*;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ApprovalCommandService {

  private final ApprovalLineStepJpaRepository approvalLineStepRepository;
  private final ApprovalLineJpaRepository approvalLineRepository;
  private final ApprovalDocumentJpaRepository approvalDocumentRepository;
  private final ApprovalHistoryJpaRepository approvalHistoryRepository;

  /**
   * 병렬 결재 승인
   */
  public void approve(Long docId, Long actorId) {

    // 1. 문서 조회
    ApprovalDocument doc = approvalDocumentRepository.findById(docId)
        .orElseThrow(() -> new BusinessException(ApprovalErrorCode.DOCUMENT_NOT_FOUND));

    // 2. 결재선 조회
    ApprovalLine line = approvalLineRepository.findByDocId(docId)
        .orElseThrow(() -> new BusinessException(ApprovalErrorCode.LINE_NOT_FOUND));

    // 3. 내가 처리할 수 있는 PENDING step 조회
    ApprovalLineStep myStep =
        approvalLineStepRepository
            .findByLineIdAndApproverIdAndApprovalStepStatus(
                line.getLineId(),
                actorId,
                ApprovalStepStatus.PENDING
            )
            .orElseThrow(() -> new BusinessException(ApprovalErrorCode.NOT_ALLOWED_APPROVER));

    // 4. 승인 처리
    myStep.approve(actorId);

    // 5. 승인 히스토리 기록
    approvalHistoryRepository.save(
        ApprovalHistory.approved(docId, actorId, myStep)
    );

    // 6. 아직 승인 안 된 결재자가 남아 있는지 확인
    boolean hasPending =
        approvalLineStepRepository.existsByLineIdAndApprovalStepStatus(
            line.getLineId(),
            ApprovalStepStatus.PENDING
        );

    // 7. 전원 승인 완료 → 문서 최종 승인
    if (!hasPending) {
      doc.approve();
    }
  }

  /**
   * 병렬 결재 반려
   */
  public void reject(Long docId, Long actorId, String reason) {

    // 1. 문서 조회
    ApprovalDocument doc = approvalDocumentRepository.findById(docId)
        .orElseThrow(() -> new BusinessException(ApprovalErrorCode.DOCUMENT_NOT_FOUND));

    // 2. 결재선 조회
    ApprovalLine line = approvalLineRepository.findByDocId(docId)
        .orElseThrow(() -> new BusinessException(ApprovalErrorCode.LINE_NOT_FOUND));

    // 3. 내가 처리할 수 있는 PENDING step 조회
    ApprovalLineStep myStep =
        approvalLineStepRepository
            .findByLineIdAndApproverIdAndApprovalStepStatus(
                line.getLineId(),
                actorId,
                ApprovalStepStatus.PENDING
            )
            .orElseThrow(() -> new BusinessException(ApprovalErrorCode.NOT_ALLOWED_APPROVER));

    // 4. 반려 처리
    myStep.reject(actorId, reason);

    // 5. 반려 히스토리 기록
    approvalHistoryRepository.save(
        ApprovalHistory.rejected(docId, actorId, myStep, reason)
    );

    // 6. 문서 반려 (즉시 종료)
    doc.reject();
  }
}
