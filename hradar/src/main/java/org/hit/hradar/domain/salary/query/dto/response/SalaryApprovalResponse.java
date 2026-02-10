package org.hit.hradar.domain.salary.query.dto.response;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hit.hradar.domain.salary.query.dto.BasicSalarySummaryDTO;
import org.hit.hradar.domain.salary.query.dto.SalaryApprovalDTO;

@Getter
@Setter
@NoArgsConstructor
public class SalaryApprovalResponse {

 private List<SalaryApprovalDTO> salaryApprovals;
 private String startDate;
 private String endDate;
 private List<BasicSalarySummaryDTO> basicSalarySummaryForFiveYear;

  public SalaryApprovalResponse(List<SalaryApprovalDTO> salaryApprovals, String startDate,
      String endDate,
      List<BasicSalarySummaryDTO> basicSalarySummaryForFiveYear) {
    this.salaryApprovals = salaryApprovals;
    this.startDate = startDate;
    this.endDate = endDate;
    this.basicSalarySummaryForFiveYear = basicSalarySummaryForFiveYear;
  }

  public SalaryApprovalResponse (List<SalaryApprovalDTO> salaryApprovals) {
    this.salaryApprovals = salaryApprovals;
  }
}
