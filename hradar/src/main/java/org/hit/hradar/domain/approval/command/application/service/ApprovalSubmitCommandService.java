package org.hit.hradar.domain.approval.command.application.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.approval.ApprovalErrorCode;
import org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalDocument;
import org.hit.hradar.domain.approval.command.domain.infrastructure.ApprovalDocumentJpaRepository;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class ApprovalSubmitCommandService {

  private final ApprovalDocumentJpaRepository approvalDocumentJpaRepository;

  //결재 문서 상신 - 임시저장(DRAFT) 상태에서만 가능

  public void submit(Long docId) {

    ApprovalDocument doc =
        approvalDocumentJpaRepository.findById(docId)
            .orElseThrow(() ->
                new BusinessException(ApprovalErrorCode.DOCUMENT_NOT_FOUND));

    // 도메인 규칙: DRAFT만 상신 가능
    doc.submit();
  }
}