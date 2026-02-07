package org.hit.hradar.domain.salary.query.dto.response;


import java.util.List;
import lombok.Getter;
import org.hit.hradar.domain.salary.query.dto.BasicSalaryDTO;
import org.hit.hradar.domain.salary.query.dto.SalaryApprovalDTO;

@Getter
public class BasicSalarySearchResponse {

  private List<BasicSalaryDTO> salaries;
  private SalaryApprovalDTO salaryApproval;

  public BasicSalarySearchResponse(List<BasicSalaryDTO> salaries
  , SalaryApprovalDTO salaryApproval) {
    this.salaries = salaries;
    this.salaryApproval = salaryApproval;
  }
}
