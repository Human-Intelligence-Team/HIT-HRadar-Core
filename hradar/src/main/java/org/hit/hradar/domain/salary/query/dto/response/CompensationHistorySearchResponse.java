package org.hit.hradar.domain.salary.query.dto.response;

import java.util.List;
import lombok.Getter;
import org.hit.hradar.domain.salary.query.dto.CompensationHistoryDTO;

@Getter
public class CompensationHistorySearchResponse {

  List<CompensationHistoryDTO> compensationSalaries;

  public CompensationHistorySearchResponse(List<CompensationHistoryDTO> compensationSalaries) {
    this.compensationSalaries = compensationSalaries;
  }


}
