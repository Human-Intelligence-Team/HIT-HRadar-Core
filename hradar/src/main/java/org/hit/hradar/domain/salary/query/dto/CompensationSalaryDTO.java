package org.hit.hradar.domain.salary.query.dto;

import lombok.Getter;
import org.hit.hradar.domain.salary.command.domain.aggregate.CompensationType;

@Getter
public class CompensationSalaryDTO {

  private CompensationType compensationType;
  private Long totalAmount;

}
