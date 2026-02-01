package org.hit.hradar.domain.approval.query.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.approval.query.dto.request.ApprovalAdminSearchRequest;
import org.hit.hradar.domain.approval.query.dto.response.ApprovalAdminDocumentResponse;
import org.hit.hradar.domain.approval.query.mapper.ApprovalAdminQueryMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApprovalAdminQueryService {

  private final ApprovalAdminQueryMapper approvalAdminQueryMapper;

  public List<ApprovalAdminDocumentResponse> findAll(
      ApprovalAdminSearchRequest request
  ) {
    return approvalAdminQueryMapper.selectAll(request);
  }
}
