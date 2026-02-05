package org.hit.hradar.domain.salary.query.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hit.hradar.domain.employee.command.domain.aggregate.EmploymentType;
import org.hit.hradar.domain.salary.command.domain.aggregate.CompensationType;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CompensationSalaryDTO {

  private String docType;
  private Long empId;
  private CompensationType compensationType;
  private String year; // 년도
  private String employeeNo; // 사번
  private String name; // 이름
  private String deptName; // 부서명
  private String positionName; // 직위명

  private Long totalBonus;
  private Long totalIncentive;

  private Long totalPerformance;
  private Long totalAllowance;

  private Long totalCompensation;
  private Long totalAmount;
  private String title;
  private LocalDate approvedAt;
  private Long docId;
  private String remark;
  private EmploymentType employmentType; // 재직상태

  public CompensationSalaryDTO(String year, Long docId, String docType, Long empId,
      String employeeNo,
      String name, String deptName, String positionName, LocalDate approvedAt, String remark,
      EmploymentType employmentType, CompensationType compensationType) {
    this.year = year;
    this.docId = docId;
    this.docType = docType;
    this.empId = empId;
    this.employeeNo = employeeNo;
    this.name = name;
    this.deptName = deptName;
    this.positionName = positionName;
    this.approvedAt = approvedAt;
    this.remark = remark;
    this.employmentType = employmentType;
    this.compensationType = compensationType;
  }



}
