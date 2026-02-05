package org.hit.hradar.domain.salary.query.dto.response;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hit.hradar.domain.salary.query.dto.SalaryApprovalDTO;

@Getter
@NoArgsConstructor
public class SalaryApprovalResponse {

 private List<SalaryApprovalDTO> salaryApprovals;


  public SalaryApprovalResponse(List<SalaryApprovalDTO> salaryApprovals) {
    this.salaryApprovals = salaryApprovals;
  }
}
