package org.hit.hradar.domain.competencyReport.query.dto.response;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hit.hradar.domain.competencyReport.query.dto.CycleDTO;

@Getter
@NoArgsConstructor
public class CycleSearchResponse {

  public List<CycleDTO> cycles;

  public CycleSearchResponse(List<CycleDTO> cycles) {
    this.cycles = cycles;
  }

}
