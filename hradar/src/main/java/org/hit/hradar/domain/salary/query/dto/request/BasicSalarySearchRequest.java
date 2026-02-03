package org.hit.hradar.domain.salary.query.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.hit.hradar.domain.employee.command.domain.aggregate.EmploymentType;

@Getter
@Setter
public class BasicSalarySearchRequest {

  private Long deptId;
  private EmploymentType employmentType;
  private String employeeNo;
  private String employeeName;


  public BasicSalarySearchRequest(Long depthId, EmploymentType employmentType, String employeeNo, String employeeName) {
    this.deptId = depthId;
    this.employmentType = employmentType;
    this.employeeNo = employeeNo;
    this.employeeName = employeeName;

  }

}
