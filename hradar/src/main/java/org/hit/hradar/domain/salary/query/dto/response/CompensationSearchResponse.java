package org.hit.hradar.domain.salary.query.dto.response;

import java.util.List;
import lombok.Getter;
import org.hit.hradar.domain.salary.query.dto.CompensationSalaryDTO;

@Getter
public class CompensationSearchResponse {

  private List<CompensationSalaryDTO> compensationSalaries;

  public CompensationSearchResponse(List<CompensationSalaryDTO> compensationSalaries) {
    this.compensationSalaries = compensationSalaries;
  }
}
