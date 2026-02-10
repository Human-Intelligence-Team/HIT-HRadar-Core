package org.hit.hradar.domain.salary.query.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hit.hradar.domain.employee.command.domain.aggregate.EmploymentType;
import org.hit.hradar.domain.salary.command.domain.aggregate.SalaryIncreaseType;

@Getter
@Setter
@NoArgsConstructor
public class BasicSalaryDTO {

  private Long basicSalaryId;
  private Long docId;
  private String docType;
  private String title;
  private Long empId;
  private Long basicSalary;
  private BigDecimal increaseRate;
  private Integer increaseAmount;
  private LocalDateTime approvedAt;
  private SalaryIncreaseType salaryIncreaseType;

  private String year; // 년도
  private String employeeNo; // 사번
  private String name; // 이름
  private String deptName; // 부서명
  private String positionName; // 직위명

  private EmploymentType employmentType; // 재직상태

  public BasicSalaryDTO(String year, Long docId, String docType, Long empId, String employeeNo,
      String name, String title, String deptName, LocalDateTime approvedAt, EmploymentType employmentType,
      String positionName, SalaryIncreaseType salaryIncreaseType) {
    this.year = year;
    this.docId = docId;
    this.docType = docType;
    this.title = title;
    this.empId = empId;
    this.employeeNo = employeeNo;
    this.name = name;
    this.deptName = deptName;
    this.positionName = positionName;
    this.approvedAt = approvedAt;
    this.salaryIncreaseType = salaryIncreaseType;
    this.employmentType = employmentType;
  }

}
