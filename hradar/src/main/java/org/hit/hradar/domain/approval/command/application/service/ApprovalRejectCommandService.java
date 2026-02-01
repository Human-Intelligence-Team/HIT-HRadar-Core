package org.hit.hradar.domain.approval.command.application.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.approval.ApprovalErrorCode;
import org.hit.hradar.domain.approval.command.domain.aggregate.*;
import org.hit.hradar.domain.approval.command.infrastructure.*;
import org.hit.hradar.domain.approval.event.ApprovalEvent;
import org.hit.hradar.domain.approval.event.ApprovalEventPublisher;
import org.hit.hradar.domain.approval.event.ApprovalEventType;
import org.hit.hradar.domain.document.command.domain.aggregate.Document;
import org.hit.hradar.domain.evaluation.command.domain.aggregate.RejectionEvent;
import org.hit.hradar.domain.evaluation.command.infrastructure.RejectionEventJpaRepository;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ApprovalRejectCommandService {

  private final ApprovalDocumentJpaRepository approvalDocumentRepository;
  private final ApprovalLineJpaRepository approvalLineRepository;
  private final ApprovalLineStepJpaRepository approvalLineStepRepository;
  private final ApprovalHistoryJpaRepository approvalHistoryRepository;
  private final ApprovalEventPublisher approvalEventPublisher;
  private final RejectionEventJpaRepository  rejectionEventRepository;
  // 결재 반려
  public void reject(Long docId, Long actorId, String reason) {

    // 0. 반려 사유 필수
    if (reason == null || reason.isBlank()) {
      throw new BusinessException(ApprovalErrorCode.REJECT_REASON_REQUIRED);
    }

    // 1. 문서 조회
    ApprovalDocument doc = approvalDocumentRepository.findById(docId)
        .orElseThrow(() -> new BusinessException(ApprovalErrorCode.DOCUMENT_NOT_FOUND));

    // 1-1. 진행 중(IN_PROGRESS) 문서만 반려 가능
    if (!doc.isApprovable()) {
      throw new BusinessException(ApprovalErrorCode.CANNOT_REJECT_NON_PENDING_STEP);
    }

    // 2. 결재선 조회
    ApprovalLine line = approvalLineRepository.findByDocId(docId)
        .orElseThrow(() -> new BusinessException(ApprovalErrorCode.LINE_NOT_FOUND));

    // 3. 현재 차례(PENDING) + 결재자/대리결재자 조회 (핵심)
    ApprovalLineStep currentStep =
        approvalLineStepRepository
            .findFirstByLineIdAndApprovalStepStatusAndApproverIdOrderByStepOrderAsc(
                line.getLineId(),
                ApprovalStepStatus.PENDING,
                actorId
            )
            .orElseGet(() ->
                approvalLineStepRepository
                    .findFirstByLineIdAndApprovalStepStatusAndProxyApproverIdOrderByStepOrderAsc(
                        line.getLineId(),
                        ApprovalStepStatus.PENDING,
                        actorId
                    )
                    .orElseThrow(() ->
                        new BusinessException(ApprovalErrorCode.NOT_ALLOWED_APPROVER)
                    )
            );

    // 4. 반려 처리
    currentStep.reject(actorId, reason);

    // 5. 반려 히스토리 기록
    approvalHistoryRepository.save(
        ApprovalHistory.rejected(docId, actorId, currentStep, reason)
    );

    //반려 횟수 증가 로직
    ApprovalDocument document = approvalDocumentRepository.findById(docId).orElseThrow(null);
    Long targetEmpId = document.getWriterId();
    rejectionEventRepository.save(
              RejectionEvent.create(targetEmpId)
    );

    // 6. 문서 상태 즉시 반려
    doc.reject();

    approvalEventPublisher.publisher(
        new ApprovalEvent(
            ApprovalEventType.REJECTED,
            docId,
            actorId,
            doc.getCompanyId()
        )
    );
  }
}
