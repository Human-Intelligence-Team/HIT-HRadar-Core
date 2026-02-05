package org.hit.hradar.domain.salary.query.dto.response;

import java.util.List;
import lombok.Getter;
import org.hit.hradar.domain.salary.query.dto.CompensationSalaryDTO;
import org.hit.hradar.domain.salary.query.dto.SalaryApprovalDTO;

@Getter
public class CompensationSearchResponse {

  private List<CompensationSalaryDTO> compensationSalaries;
  private SalaryApprovalDTO salaryApproval;

  public CompensationSearchResponse(List<CompensationSalaryDTO> compensationSalaries
  , SalaryApprovalDTO salaryApproval) {
    this.compensationSalaries = compensationSalaries;
    this.salaryApproval = salaryApproval;
  }
}
