package org.hit.hradar.domain.competencyReport.gemini.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hit.hradar.domain.competencyReport.query.dto.ContentRowDTO;

@Getter
@AllArgsConstructor
public class OutputResultDTO {

  private Long ownerId;
  private Long cycleId;
  private String kpiOkrResultSummary; // KPI/ OKR 달성 결과 요약
  private String goalFailureAnalysis; // 미달성 목표 원인 분석 (역량 관점)
  private List<ContentResultDTO> contentRow; // 학습컨텐츠-태그

}
