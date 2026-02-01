package org.hit.hradar.domain.salary.query.dto.request;

import lombok.Getter;
import org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalStatus;
import org.hit.hradar.domain.employee.command.domain.aggregate.EmploymentType;

@Getter
public class SalaryApprovalTargetRequest {

  private ApprovalStatus approvalStatus;  // 결재 상태
  private String approvalDocumentType;  // 결재 문서 타입
  private EmploymentType employmentType;  // 재직 상태
  private Long employeeNo;
  private Long deptId;
  private String employeeName;
  private Long positionId;

  public SalaryApprovalTargetRequest(String approvalDocumentType, EmploymentType employmentType, Long employeeNo, Long deptId, String employeeName, Long positionId) {
    this.approvalStatus = ApprovalStatus.APPROVED;
    this.approvalDocumentType = approvalDocumentType;
    this.employmentType = employmentType;
    this.employeeNo = employeeNo;
    this.deptId = deptId;
    this.employeeName = employeeName;
    this.positionId = positionId;

  }
}