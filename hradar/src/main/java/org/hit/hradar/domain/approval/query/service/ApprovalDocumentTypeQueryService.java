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

  public List<ApprovalDocumentTypeResponse> findAllActiveTypes() {
    return approvalDocumentTypeJpaRepository
        .findByActiveTrueAndIsTemplateTrue()
        .stream()
        .map(d -> new ApprovalDocumentTypeResponse(
            d.getDocId(),
            d.getDocType(),
            d.getName(),
            d.isActive()
        ))
        .toList();
  }
}