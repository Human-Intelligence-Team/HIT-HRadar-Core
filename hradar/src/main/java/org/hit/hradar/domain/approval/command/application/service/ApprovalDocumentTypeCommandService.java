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
            active,
            req.getAttendanceCategory(),
            req.getOvertimeMinutes()
        )
    );
  }
  public void update(Long typeId, ApprovalDocumentTypeRequest req) {
    ApprovalDocumentType type = approvalDocumentTypeJpaRepository.findById(typeId)
        .orElseThrow();

    boolean active = (req.getActive() == null) ? type.isActive() : req.getActive();
    type.update(req.getName(), active, req.getAttendanceCategory(), req.getOvertimeMinutes());
  }

  public void delete(Long typeId) {
    approvalDocumentTypeJpaRepository.deleteById(typeId);
  }
}
