package org.hit.hradar.domain.salary.query.dto.response;

import java.util.List;
import lombok.Getter;
import org.hit.hradar.domain.salary.query.dto.SalaryDTO;

@Getter
public class SalarySearchResponse {

  private List<SalaryDTO> salaries;

  public  SalarySearchResponse(List<SalaryDTO> salaries) {
    this.salaries = salaries;
  }
}
