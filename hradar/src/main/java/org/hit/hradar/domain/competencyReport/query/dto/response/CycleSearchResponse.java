package org.hit.hradar.domain.competencyReport.query.dto.response;

import java.util.List;
import lombok.Getter;
import org.hit.hradar.domain.competencyReport.query.dto.CycleDTO;

@Getter
public class CycleSearchResponse {

  public List<CycleDTO> cycles;

  public CycleSearchResponse(List<CycleDTO> cycles) {
    this.cycles = cycles;
  }

}
