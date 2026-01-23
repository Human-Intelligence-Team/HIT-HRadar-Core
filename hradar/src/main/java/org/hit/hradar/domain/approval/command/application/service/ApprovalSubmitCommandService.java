package org.hit.hradar.domain.approval.command.application.service;

import static org.hit.hradar.domain.approval.ApprovalErrorCode.LINE_NOT_FOUND;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.approval.ApprovalErrorCode;
import org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalDocument;
import org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalHistory;
import org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalLine;
import org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalLineStep;
import org.hit.hradar.domain.approval.command.infrastructure.ApprovalDocumentJpaRepository;
import org.hit.hradar.domain.approval.command.infrastructure.ApprovalHistoryJpaRepository;
import org.hit.hradar.domain.approval.command.infrastructure.ApprovalLineJpaRepository;

import org.hit.hradar.domain.approval.command.infrastructure.ApprovalLineStepJpaRepository;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class ApprovalSubmitCommandService {

  private final ApprovalDocumentJpaRepository approvalDocumentJpaRepository;
  private final ApprovalLineJpaRepository approvalLineJpaRepository;
  private final ApprovalHistoryJpaRepository approvalHistoryJpaRepository;
  private final ApprovalLineStepJpaRepository approvalLineStepJpaRepository;


  //결재 문서 상신 - 임시저장(DRAFT) 상태에서만 가능
  public void submit(Long docId,Long actorId) {

    //문서 조회
    ApprovalDocument doc =
        approvalDocumentJpaRepository.findById(docId)
            .orElseThrow(() ->
                new BusinessException(ApprovalErrorCode.DOCUMENT_NOT_FOUND));

    //결재선 존재 여부 검증
    ApprovalLine line =
        approvalLineJpaRepository.findByDocId(docId)
            .orElseThrow(() ->
                new BusinessException((LINE_NOT_FOUND)));

    // =====기안자 본인 검증 =====
    if (!doc.isOwner(actorId)) {
      throw new BusinessException(ApprovalErrorCode.NOT_ALLOWED_SUBMIT);
    }

    // 도메인 규칙: DRAFT만 상신 가능
    doc.submit(actorId);

    //PENDING(결재대기)는 SUBMIT이후에만
    ApprovalLineStep firstStep =
        approvalLineStepJpaRepository
            .findFirstByLineIdOrderByStepOrderAsc(
                line.getLineId())
            .orElseThrow(() ->
                new BusinessException(ApprovalErrorCode.NO_PENDING_STEP));
    firstStep.changeToPending();

    //히스토리 기록
    approvalHistoryJpaRepository.save(
        ApprovalHistory.submit(docId, actorId)
    );
  }
}