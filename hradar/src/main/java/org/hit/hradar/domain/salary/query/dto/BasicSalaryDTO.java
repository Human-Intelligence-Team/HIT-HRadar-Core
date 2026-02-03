package org.hit.hradar.domain.salary.query.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import org.hit.hradar.domain.employee.command.domain.aggregate.EmploymentType;

@Getter
public class BasicSalaryDTO {

  private Long basicSalaryId;
  private Long docId;
  private String title;
  private Long empId;
  private Long basicSalary;
  private BigDecimal increaseRate;
  private Integer increaseAmount;
  private LocalDateTime approvedAt;

  private String year; // 년도
  private String employeeNo; // 사번
  private String name; // 이름
  private String deptName; // 부서명
  private String positionName; // 직위명

  private EmploymentType employmentType; // 재직상태

}
