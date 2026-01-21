package org.hit.hradar.domain.salary.query.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.hit.hradar.domain.employee.command.domain.aggregate.Employee;
import org.hit.hradar.domain.employee.domain.aggregate.EmpolymentType;

@Getter
@Setter
public class BasicSalarySearchRequest {

  private Long deptId;
  private EmpolymentType employmentType;
  private String employeeNo;
  private String employeeName;


  public BasicSalarySearchRequest(Long depthId, EmpolymentType employmentType, String employeeNo, String employeeName) {
    this.deptId = depthId;
    this.employmentType = employmentType;
    this.employeeNo = employeeNo;
    this.employeeName = employeeName;

  }

}
