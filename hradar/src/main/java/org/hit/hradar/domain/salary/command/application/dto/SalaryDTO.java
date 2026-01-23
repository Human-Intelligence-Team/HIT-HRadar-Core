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
}
