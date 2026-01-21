package org.hit.hradar.domain.salary.query.dto.response;

import java.util.List;
import lombok.Getter;
import org.hit.hradar.domain.salary.query.dto.BasicSalaryHistoryDTO;

@Getter
public class BasicSalaryHistoryResponse {

  private BasicSalaryHistoryDTO salaryHistory;
  private List<BasicSalaryHistoryDTO>  basicSalaryHistories;

  public BasicSalaryHistoryResponse(BasicSalaryHistoryDTO salaryHistory,  List<BasicSalaryHistoryDTO> basicSalaryHistories) {
    this.salaryHistory = salaryHistory;
    this.basicSalaryHistories = basicSalaryHistories;
  }
}
