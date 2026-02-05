package org.hit.hradar.domain.salary.query.dto.request;

import lombok.Getter;
import org.hit.hradar.domain.employee.command.domain.aggregate.EmploymentType;
import org.hit.hradar.domain.salary.command.domain.aggregate.CompensationType;

@Getter
public class CompensationSearchRequest {

  private Long docId;
  private Long comId;
  private Long deptId;
  private Long comPositionId;
  private EmploymentType employmentType;
  private String employeeNo;
  private String employeeName;

  private String startDate;
  private String endDate;

  public CompensationSearchRequest(Long deptId, Long comPositionId, EmploymentType employmentType,
      String employeeNo, String employeeName, String year, String month) {
    this.deptId = deptId;
    this.comPositionId = comPositionId;
    this.employmentType = employmentType;
    this.employeeNo = employeeNo;
    this.employeeName = employeeName;

  }

  public void setDocId(Long docId) {
    this.docId = docId;
  }

  public void setComId(Long comId) {
    this.comId = comId;
  }
}