package org.hit.hradar.domain.salary.query.dto;

import lombok.Getter;

@Getter
public class SalaryApprovalTargetDTO {

  private String employeeNo; // 사번
  private String name; // 이름
  private String deptName; // 부서명
  private String positionName; // 직위명

}
