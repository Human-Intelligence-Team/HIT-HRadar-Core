package org.hit.hradar.domain.salary.query.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hit.hradar.domain.salary.command.domain.aggregate.SalaryIncreaseType;

@Getter
@NoArgsConstructor
public class BasicSalaryHistoryDTO {

  private Long empId;
  private String year;
  private String title;
  private Long prevSalary;
  private Long currentSalary;
  private BigDecimal increaseRate;
  private LocalDateTime approvedAt;
  private SalaryIncreaseType salaryIncreaseType;

  public BasicSalaryHistoryDTO(Long empId, String year, String title, Long prevSalary, Long currentSalary,  BigDecimal increaseRate, LocalDateTime approvedAt
  , SalaryIncreaseType salaryIncreaseType) {
    this.empId = empId;
    this.year = year;
    this.title = title;
    this.prevSalary = prevSalary;
    this.currentSalary = currentSalary;
    this.increaseRate = increaseRate;
    this.approvedAt = approvedAt;
    this.salaryIncreaseType = salaryIncreaseType;
  }

  public static BasicSalaryHistoryDTO create (Long empId, String year, String title, Long prevSalary, Long currentSalary,  BigDecimal increaseRate, LocalDateTime approvedAt, SalaryIncreaseType salaryIncreaseType) {
   return new BasicSalaryHistoryDTO(empId, year, title, prevSalary, currentSalary, increaseRate, approvedAt,  salaryIncreaseType);
  }

}
