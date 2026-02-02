package org.hit.hradar.domain.salary.command.application.dto;

import java.math.BigDecimal;
import lombok.Getter;
import org.hit.hradar.domain.salary.command.domain.aggregate.CompensationType;
import org.hit.hradar.domain.salary.command.domain.aggregate.SalaryIncreaseType;

@Getter
public class SalaryDTO {

  // 공통
  private Long empId;
  private String remark;

  // 기본급
  private Long basicSalaryId;
  private SalaryIncreaseType salaryIncreaseType;
  private Long basicSalary;
  private BigDecimal increaseRate;
  private Integer increaseAmount;

  // 변동보상
  private Long compensationSalaryId;
  private CompensationType compensationType;
  private Long amount;
  private BigDecimal rate;

  public SalaryDTO(Long empId, String remark,
      Long basicSalaryId, SalaryIncreaseType salaryIncreaseType, Long basicSalary, BigDecimal increaseRate, Integer increaseAmount,
      Long compensationSalaryId, CompensationType compensationType, Long amount, BigDecimal rate) {
    this.empId = empId;
    this.remark = remark;
    this.basicSalaryId = basicSalaryId;
    this.salaryIncreaseType = salaryIncreaseType;
    this.basicSalary = basicSalary;
    this.increaseRate = increaseRate;
    this.increaseAmount = increaseAmount;
    this.compensationSalaryId = compensationSalaryId;
    this.compensationType = compensationType;
    this.amount = amount;
    this.rate = rate;
  }
}
