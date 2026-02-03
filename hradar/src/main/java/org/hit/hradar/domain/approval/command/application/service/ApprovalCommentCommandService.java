package org.hit.hradar.domain.approval.command.application.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.approval.ApprovalErrorCode;
import org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalComment;
import org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalDocument;
import org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalStatus;
import org.hit.hradar.domain.approval.command.infrastructure.ApprovalCommentJpaRepository;
import org.hit.hradar.domain.approval.command.infrastructure.ApprovalDocumentJpaRepository;
import org.hit.hradar.domain.approval.query.mapper.ApprovalAccessQueryMapper;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ApprovalCommentCommandService {

  private final ApprovalDocumentJpaRepository approvalDocumentRepository;
  private final ApprovalCommentJpaRepository approvalCommentJpaRepository;
  private final ApprovalAccessQueryMapper approvalAccessQueryMapper;

  public void addComment(
      Long docId,
      Long writerId,
      String content,
      Long parentCommentId,
      Long accountId
  ) {
    if (content == null || content.isBlank()) {
      throw new BusinessException(ApprovalErrorCode.INVALID_REQUEST);
    }

    ApprovalDocument doc =
        approvalDocumentRepository.findById(docId)
            .orElseThrow(() -> new BusinessException(ApprovalErrorCode.DOCUMENT_NOT_FOUND));

    if (doc.getStatus() == ApprovalStatus.DRAFT
        || doc.getStatus() == ApprovalStatus.WITHDRAWN) {
      throw new BusinessException(ApprovalErrorCode.NOT_ALLOWED_COMMENT);
    }

    if (!approvalAccessQueryMapper.existsAccessibleUser(docId, writerId, accountId)) {
      throw new BusinessException(ApprovalErrorCode.NOT_ALLOWED_APPROVER);
    }

    approvalCommentJpaRepository.save(
        ApprovalComment.create(docId, writerId, content, parentCommentId)
    );
  }
}