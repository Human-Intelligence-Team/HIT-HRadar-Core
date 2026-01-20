package org.hit.hradar.domain.approval.command.application.service;

import static org.hit.hradar.domain.approval.ApprovalErrorCode.LINE_NOT_FOUND;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.approval.ApprovalErrorCode;
import org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalDocument;
import org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalHistory;
import org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalLine;
import org.hit.hradar.domain.approval.command.domain.infrastructure.ApprovalDocumentJpaRepository;
import org.hit.hradar.domain.approval.command.domain.infrastructure.ApprovalHistoryJpaRepository;
import org.hit.hradar.domain.approval.command.domain.infrastructure.ApprovalLineJpaRepository;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class ApprovalSubmitCommandService {

  private final ApprovalDocumentJpaRepository approvalDocumentJpaRepository;
  private final ApprovalLineJpaRepository approvalLineJpaRepository;
  private final ApprovalHistoryJpaRepository approvalHistoryJpaRepository;

  //결재 문서 상신 - 임시저장(DRAFT) 상태에서만 가능

  public void submit(Long docId,Long actorId) {

    ApprovalDocument doc =
        approvalDocumentJpaRepository.findById(docId)
            .orElseThrow(() ->
                new BusinessException(ApprovalErrorCode.DOCUMENT_NOT_FOUND));

    ApprovalLine line =
        approvalLineJpaRepository.findByDocId(docId)
            .orElseThrow(() ->
                new BusinessException((LINE_NOT_FOUND)));

    // 도메인 규칙: DRAFT만 상신 가능
    doc.submit(actorId);

    //결재선 시작(currentStep = 1)
    line.start();

    //히스토리 기록
    approvalHistoryJpaRepository.save(
        ApprovalHistory.submit(docId, actorId)
    );
  }
}