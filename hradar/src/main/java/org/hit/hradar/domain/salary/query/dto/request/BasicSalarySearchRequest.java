package org.hit.hradar.domain.salary.query.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.hit.hradar.domain.employee.command.domain.aggregate.EmploymentType;

@Getter
@Setter
public class BasicSalarySearchRequest {

  private Long docId;
  private Long comId;
  private Long deptId;
  private Long comPositionId;
  private EmploymentType employmentType;
  private String employeeNo;
  private String employeeName;

  public BasicSalarySearchRequest(Long deptId, Long comPositionId, EmploymentType employmentType,
      String employeeNo, String employeeName) {
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
