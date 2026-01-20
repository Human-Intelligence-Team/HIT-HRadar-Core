package org.hit.hradar.domain.salary.query.dto.response;

import java.util.List;
import lombok.Getter;
import org.hit.hradar.domain.salary.query.dto.SalaryHistoryDTO;

@Getter
public class SalaryHistoryResponse {

  private SalaryHistoryDTO salaryHistoryDTO;
  private List<SalaryHistoryDTO>  salaryHistoryDTOList;
}
