package org.hit.hradar.domain.salary.query.dto.response;

import lombok.Getter;

@Getter
public class AnnualCompensationSummaryResponse {

    private Long basicSalary; // 기본급
    private Long totalBonus; // 총 상여금
    private Long totalPerformance; // 총 성과금
    private Long totalIncentive; // 총 인센티브
    private Long totalAllowance;// 총 기타 수당
    private Long salary; // 총 연봉

  public AnnualCompensationSummaryResponse(Long basicSalary, Long totalBonus, Long totalPerformance, Long totalIncentive, Long totalAllowance, Long salary) {
    this.basicSalary = basicSalary;
    this.totalBonus = totalBonus;
    this.totalPerformance = totalPerformance;
    this.totalIncentive = totalIncentive;
    this.totalAllowance = totalAllowance;
    this.salary = salary;
  }

}
