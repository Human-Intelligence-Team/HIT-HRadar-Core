package org.hit.hradar.domain.salary.query.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import org.hit.hradar.domain.employee.domain.aggregate.EmpolymentType;

@Getter
public class SalaryDTO {

  // 공통
  private String year; // 년도
  private String empId; // 사원 pk
  private String employeeNo; // 사번
  private String name; // 이름
  private String deptName; // 부서명
  private String positionName; // 직위명
  private LocalDateTime approvedAt; // 결재일시

  // 인사팀
  private EmpolymentType empolymentType; // 재직상태

}
