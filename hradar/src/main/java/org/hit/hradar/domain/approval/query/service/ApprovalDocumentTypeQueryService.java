package org.hit.hradar.domain.approval.query.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.approval.command.infrastructure.ApprovalDocumentTypeJpaRepository;
import org.hit.hradar.domain.approval.query.dto.response.ApprovalDocumentTypeResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApprovalDocumentTypeQueryService {

  private final ApprovalDocumentTypeJpaRepository approvalDocumentTypeJpaRepository;

  public List<ApprovalDocumentTypeResponse> findAllActiveTypes(Long companyId) {
    return approvalDocumentTypeJpaRepository.findByCompanyIdAndActiveTrue(companyId)
        .stream()
        .map(t -> new ApprovalDocumentTypeResponse(
            t.getTypeId(),
            t.getDocType(),
            t.getName(),
            t.isActive()
        ))
        .toList();
  }
}