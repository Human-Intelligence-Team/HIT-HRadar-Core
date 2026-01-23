package org.hit.hradar.domain.salary.query.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hit.hradar.domain.salary.command.domain.aggregate.CompensationType;

@Getter
@AllArgsConstructor
public class CompensationSalaryDTO {

  private CompensationType compensationType;
  private Long totalBonus;
  private Long totalIncentive;
  private Long totalPerformance;
  private Long totalAllowance;
  private Long totalCompensation;

  private Long totalAmount;
  private String title;
  private LocalDate approvedAt;


}
