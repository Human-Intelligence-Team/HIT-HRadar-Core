package org.hit.hradar.domain.salary.query.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;

@Getter
public class SalaryHistoryDTO {

  private Long empId;
  private String year;
  private String content;
  private Long prevSalary;
  private Long currentSalary;
  private BigDecimal increaseRate;
  private LocalDate approvedAt;

  public SalaryHistoryDTO(Long empId, String year, String content, Long prevSalary, Long currentSalary,  BigDecimal increaseRate, LocalDate approvedAt) {
    this.empId = empId;
    this.year = year;
    this.content = content;
    this.prevSalary = prevSalary;
    this.currentSalary = currentSalary;
    this.increaseRate = increaseRate;
    this.approvedAt = approvedAt;
  }

}
