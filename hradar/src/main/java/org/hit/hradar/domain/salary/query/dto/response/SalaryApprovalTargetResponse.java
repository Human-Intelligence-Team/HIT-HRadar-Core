package org.hit.hradar.domain.salary.query.dto.response;

import java.util.List;
import lombok.Getter;
import org.hit.hradar.domain.salary.query.dto.SalaryApprovalTargetDTO;

@Getter
public class SalaryApprovalTargetResponse {

  private List<SalaryApprovalTargetDTO>  salaryApprovalTargets;

  public SalaryApprovalTargetResponse(List<SalaryApprovalTargetDTO> salaryApprovalTargets) {
    this.salaryApprovalTargets = salaryApprovalTargets;
  }
}
