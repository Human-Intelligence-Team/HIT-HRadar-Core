package org.hit.hradar.domain.salary.query.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hit.hradar.domain.employee.command.domain.aggregate.EmploymentType;
import org.hit.hradar.domain.salary.command.domain.aggregate.CompensationType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompensationSearchRequest {

  private Long docId;
  private Long comId;
  private Long deptId;
  private Long empId;
  private Long comPositionId;
  private EmploymentType employmentType;
  private String employeeNo;
  private String employeeName;

  private String startDate;
  private String endDate;

  public CompensationSearchRequest(Long deptId, Long comPositionId, EmploymentType employmentType,
      String employeeNo, String employeeName) {
    this.deptId = deptId;
    this.comPositionId = comPositionId;
    this.employmentType = employmentType;
    this.employeeNo = employeeNo;
    this.employeeName = employeeName;

  }

  public CompensationSearchRequest(Long empId, Long comId, String startDate, String endDate) {
    this.empId = empId;
    this.comId = comId;
    this.startDate = startDate;
    this.endDate = endDate;
  }
}