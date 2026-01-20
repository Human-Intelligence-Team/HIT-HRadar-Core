package org.hit.hradar.domain.salary.query.dto.response;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.hit.hradar.domain.salary.query.dto.SalaryHistoryDTO;

@Getter
public class SalaryHistoryResponse {

  private SalaryHistoryDTO salaryHistoryDTO;
  private List<SalaryHistoryDTO>  salaryHistoryDTOList;

  public SalaryHistoryResponse(SalaryHistoryDTO salaryHistoryDTO,  List<SalaryHistoryDTO> salaryHistoryDTOList) {
    this.salaryHistoryDTO = salaryHistoryDTO;
    this.salaryHistoryDTOList = new ArrayList<>();
  }
}
