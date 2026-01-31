package org.hit.hradar.domain.approval.command.application.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.approval.command.application.dto.request.ApprovalDocumentTypeRequest;
import org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalDocumentType;
import org.hit.hradar.domain.approval.command.infrastructure.ApprovalDocumentTypeJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ApprovalDocumentTypeCommandService {

  private final ApprovalDocumentTypeJpaRepository approvalDocumentTypeJpaRepository;

  public void create(Long companyId, ApprovalDocumentTypeRequest req) {
    boolean active = (req.getActive() == null) ? true : req.getActive();

    approvalDocumentTypeJpaRepository.save(
        new ApprovalDocumentType(
            companyId,
            req.getDocType(),
            req.getName(),
            active
        )
    );
  }
  public void update(Long typeId, ApprovalDocumentTypeRequest req) {
    ApprovalDocumentType type = approvalDocumentTypeJpaRepository.findById(typeId)
        .orElseThrow();

    type.updateName(req.getName());
  }

  public void delete(Long typeId) {
    approvalDocumentTypeJpaRepository.deleteById(typeId);
  }
}
