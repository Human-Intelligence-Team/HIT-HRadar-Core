package org.hit.hradar.domain.approval.query.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.approval.command.infrastructure.ApprovalDocumentTypeJpaRepository;
import org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalAttendanceCategory;
import org.hit.hradar.domain.approval.query.dto.response.ApprovalDocumentTypeResponse;
import org.hit.hradar.domain.leave.query.service.LeavePolicyQueryService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApprovalDocumentTypeQueryService {

  private final ApprovalDocumentTypeJpaRepository approvalDocumentTypeJpaRepository;
  private final LeavePolicyQueryService leavePolicyQueryService;

  public List<ApprovalDocumentTypeResponse> findAllActiveTypes(Long companyId) {
    List<ApprovalDocumentTypeResponse> responseList = new ArrayList<>(
        approvalDocumentTypeJpaRepository.findByCompanyIdAndActiveTrue(companyId)
            .stream()
            .map(t -> new ApprovalDocumentTypeResponse(
                t.getTypeId(),
                t.getDocType(),
                t.getName(),
                t.isActive(),
                t.getAttendanceCategory(),
                t.getOvertimeMinutes()
            ))
            .toList()
    );

    responseList.addAll(
        leavePolicyQueryService.getPolicies(companyId)
            .stream()
            .map(p -> new ApprovalDocumentTypeResponse(
                p.getPolicyId(),
                p.getTypeCode(),
                p.getTypeName(),
                'Y' == p.getIsActive(),
                ApprovalAttendanceCategory.VACATION,
                0
            ))
            .toList()
    );

    return responseList;
  }
}