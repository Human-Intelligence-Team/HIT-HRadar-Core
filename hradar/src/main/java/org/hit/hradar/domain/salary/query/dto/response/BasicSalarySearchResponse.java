package org.hit.hradar.domain.salary.query.dto.response;


import java.util.List;
import lombok.Getter;
import org.hit.hradar.domain.salary.query.dto.BasicSalaryDTO;

@Getter
public class BasicSalarySearchResponse {

  private List<BasicSalaryDTO> salaries;

  public BasicSalarySearchResponse(List<BasicSalaryDTO> salaries) {
    this.salaries = salaries;
  }
}
