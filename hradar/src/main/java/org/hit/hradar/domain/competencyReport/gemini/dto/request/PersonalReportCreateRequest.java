package org.hit.hradar.domain.competencyReport.gemini.dto.request;

import java.util.List;
import lombok.Getter;
import org.hit.hradar.domain.competencyReport.command.application.dto.KpiDataDTO;
import org.hit.hradar.domain.competencyReport.command.application.dto.OkrDataDTO;

@Getter
public class PersonalReportCreateRequest {

  private String deptName;
  private List<KpiDataDTO> kpiList;
  private List<OkrDataDTO> okrList;
}
