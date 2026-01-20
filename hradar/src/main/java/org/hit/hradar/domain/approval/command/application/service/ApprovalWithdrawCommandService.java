package org.hit.hradar.domain.approval.command.application.service;

import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.approval.ApprovalErrorCode;
import org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalDocument;
import org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalHistory;
import org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalLine;
import org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalStepStatus;
import org.hit.hradar.domain.approval.command.domain.infrastructure.ApprovalDocumentJpaRepository;
import org.hit.hradar.domain.approval.command.domain.infrastructure.ApprovalHistoryJpaRepository;
import org.hit.hradar.domain.approval.command.domain.infrastructure.ApprovalLineJpaRepository;
import org.hit.hradar.domain.approval.command.domain.infrastructure.ApprovalLineStepJpaRepository;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class ApprovalWithdrawCommandService {

  private final ApprovalDocumentJpaRepository approvalDocumentJpaRepository;
  private final ApprovalLineStepJpaRepository approvalLineStepJpaRepository;
  private final ApprovalLineJpaRepository approvalLineJpaRepository;
  private final ApprovalHistoryJpaRepository approvalHistoryJpaRepository;

  //결재 문서 회수DRAFT 상태, 상신되었지만 아직 승인/반려 이력이 없는 경우
  public void withdraw(Long docId, Long actorId) {

    //결재 문서 조회
    ApprovalDocument doc =
        approvalDocumentJpaRepository.findById(docId)
            .orElseThrow(() ->
                new BusinessException(ApprovalErrorCode.DOCUMENT_NOT_FOUND));

    // 2. 결재선 조회
    ApprovalLine line =
        approvalLineJpaRepository.findByDocId(docId)
            .orElseThrow(() ->
                new BusinessException(ApprovalErrorCode.LINE_NOT_FOUND));

    // 승인/반려 이력 존재 여부
    boolean hasApprovalHistory =
        approvalLineStepJpaRepository.existsByLineIdAndApprovalStepStatusIn(
            line.getLineId(),
            List.of(
                ApprovalStepStatus.APPROVED,
                ApprovalStepStatus.REJECTED
            )
        );

    if (hasApprovalHistory) {
      throw new BusinessException(
          ApprovalErrorCode.CANNOT_WITHDRAW_AFTER_APPROVAL_STARTED
      );
    }

    //문서 회수
    doc.withdraw(actorId);

    //회수 히스토리 저장
    approvalHistoryJpaRepository.save(
        ApprovalHistory.withdraw(docId, actorId)
    );
  }
}
