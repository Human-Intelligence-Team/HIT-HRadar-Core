package org.hit.hradar.domain.salary.query.dto.response;

import lombok.Getter;
import org.hit.hradar.domain.salary.query.dto.CompensationSalaryDTO;

@Getter
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
