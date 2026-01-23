package org.hit.hradar.domain.salary.query.dto;

import lombok.Getter;
import org.hit.hradar.domain.employee.command.domain.aggregate.EmploymentType;

@Getter
public class SalaryApprovalTargetDTO {

  private Long empId;
  private String employeeNo; // 사번
  private String deptName; // 부서명
  private String employeeName; // 사원명
  private String positionName; // 직위명
  private EmploymentType employmentType; // 재직상태

}
