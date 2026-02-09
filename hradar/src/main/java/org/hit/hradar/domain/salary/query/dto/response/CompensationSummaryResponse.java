package org.hit.hradar.domain.salary.query.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.hit.hradar.domain.salary.query.dto.CompensationSalaryDTO;

@Getter
@Setter
public class CompensationSummaryResponse {
  
  private String startDate;
  private String endDate;
  private CompensationSalaryDTO  compensationSalary;
  
  
  public CompensationSummaryResponse(String startDate, String endDate, CompensationSalaryDTO compensationSalary) {
    this.startDate = startDate;
    this.endDate = endDate;
    this.compensationSalary = compensationSalary;
  }
  
}
