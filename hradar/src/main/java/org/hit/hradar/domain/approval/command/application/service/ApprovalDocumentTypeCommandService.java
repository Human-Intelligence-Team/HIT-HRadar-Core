package org.hit.hradar.domain.approval.command.application.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.approval.command.application.dto.request.ApprovalDocumentTypeRequest;
import org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalDocument;
import org.hit.hradar.domain.approval.command.infrastructure.ApprovalDocumentTypeJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ApprovalDocumentTypeCommandService {

  private final ApprovalDocumentTypeJpaRepository approvalDocumentTypeJpaRepository;

  public void create(ApprovalDocumentTypeRequest req) {
    approvalDocumentTypeJpaRepository.save(
        new ApprovalDocument(
            req.getDocType(),
            req.getName(),
            true
        )
    );
  }

  public void update(Long docId, ApprovalDocumentTypeRequest req) {
    ApprovalDocument type = approvalDocumentTypeJpaRepository.findById(docId)
        .orElseThrow();

    type.updateName(req.getName());
  }

  public void delete(Long docId) {
    approvalDocumentTypeJpaRepository.deleteById(docId);
  }
}