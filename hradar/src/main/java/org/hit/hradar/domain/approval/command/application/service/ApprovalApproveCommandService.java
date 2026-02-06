package org.hit.hradar.domain.approval.command.application.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.approval.ApprovalErrorCode;
import org.hit.hradar.domain.approval.command.domain.aggregate.*;
import org.hit.hradar.domain.approval.command.infrastructure.*;
import org.hit.hradar.domain.approval.event.ApprovalEvent;
import org.hit.hradar.domain.approval.event.ApprovalEventPublisher;
import org.hit.hradar.domain.approval.event.ApprovalEventType;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ApprovalApproveCommandService {

  private final ApprovalLineStepJpaRepository approvalLineStepRepository;
  private final ApprovalLineJpaRepository approvalLineRepository;
  private final ApprovalDocumentJpaRepository approvalDocumentRepository;
  private final ApprovalHistoryJpaRepository approvalHistoryRepository;
  private final ApprovalEventPublisher approvalEventPublisher;

  // 문서 승인
  public void approve(Long docId, Long employeeId, Long accountId, Long companyId) {

    // 1. 문서 조회 (회사 스코프 포함)
    ApprovalDocument doc =
        approvalDocumentRepository.findByDocIdAndCompanyId(docId, companyId)
            .orElseThrow(() -> new BusinessException(ApprovalErrorCode.DOCUMENT_NOT_FOUND));

    // 1-1. 진행 중(IN_PROGRESS) 문서만 승인 가능
    if (!doc.isApprovable()) {
      throw new BusinessException(ApprovalErrorCode.CANNOT_APPROVE_NON_PENDING_STEP);
    }

    // 2. 결재선 조회
    ApprovalLine line = approvalLineRepository.findByDocId(docId)
        .orElseThrow(() -> new BusinessException(ApprovalErrorCode.LINE_NOT_FOUND));

    // 3. 현재 차례(PENDING) + 결재자/대리결재자 조회 (핵심 - AccountID 사용)
    ApprovalLineStep currentStep =
        approvalLineStepRepository
            .findFirstByLineIdAndApprovalStepStatusAndApproverIdOrderByStepOrderAsc(
                line.getLineId(),
                ApprovalStepStatus.PENDING,
                accountId
            )
            .orElseGet(() ->
                approvalLineStepRepository
                    .findFirstByLineIdAndApprovalStepStatusAndProxyApproverIdOrderByStepOrderAsc(
                        line.getLineId(),
                        ApprovalStepStatus.PENDING,
                        accountId
                    )
                    .orElseThrow(() ->
                        new BusinessException(ApprovalErrorCode.NOT_ALLOWED_APPROVER)
                    )
            );

    // 4. 승인 처리 (Validation requires AccountID)
    currentStep.approve(accountId);

    // 5. 다음 WAITING 결재자를 PENDING으로 전환
    approvalLineStepRepository
        .findFirstByLineIdAndApprovalStepStatusOrderByStepOrderAsc(
            line.getLineId(),
            ApprovalStepStatus.WAITING
        )
        .ifPresent(ApprovalLineStep::changeToPending);

    // 6. 승인 히스토리 기록 (EmployeeID 사용)
    approvalHistoryRepository.save(
        ApprovalHistory.approved(docId, employeeId, currentStep)
    );

    // 7. 더 이상 PENDING 없으면 문서 최종 승인
    boolean hasPending =
        approvalLineStepRepository.existsByLineIdAndApprovalStepStatus(
            line.getLineId(),
            ApprovalStepStatus.PENDING
        );

    if (!hasPending) {
      doc.approve();
    }

    approvalEventPublisher.publisher(
        new ApprovalEvent(
            ApprovalEventType.APPROVED,
            docId,
            doc.getCompanyId(),
            employeeId
        )
    );

  }
}
