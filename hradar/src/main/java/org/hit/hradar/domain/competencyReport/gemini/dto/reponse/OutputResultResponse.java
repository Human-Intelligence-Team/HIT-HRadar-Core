package org.hit.hradar.domain.competencyReport.gemini.dto.reponse;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hit.hradar.domain.competencyReport.gemini.dto.ContentResultDTO;

@Getter
@AllArgsConstructor
public class OutputResultResponse {

  private Long empId;
  private Long cycleId;
  private LocalDateTime startDate;
  private LocalDateTime endDate;
  private String kpiOkrResultSummary;
  private String goalFailureAnalysis;
  private List<ContentResultDTO> contentRow;


}
