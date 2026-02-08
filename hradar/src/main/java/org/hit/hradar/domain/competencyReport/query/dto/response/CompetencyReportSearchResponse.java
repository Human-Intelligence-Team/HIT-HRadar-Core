package org.hit.hradar.domain.competencyReport.query.dto.response;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hit.hradar.domain.competencyReport.query.dto.CompetencyReportDTO;

@Getter
@NoArgsConstructor
public class CompetencyReportSearchResponse {

  private List<CompetencyReportDTO> reports;

  public CompetencyReportSearchResponse(List<CompetencyReportDTO> reports) {
    this.reports = reports;
  }
}
