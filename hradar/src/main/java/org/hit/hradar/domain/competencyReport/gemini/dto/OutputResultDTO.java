package org.hit.hradar.domain.competencyReport.gemini.dto;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OutputResultDTO {

  private Long ownerId;
  private Long cycleId;
  private String kpiOkrResultSummary; // KPI/ OKR 달성 결과 요약
  private String goalFailureAnalysis; // 미달성 목표 원인 분석 (역량 관점)
  private List<ContentResultDTO> contentRow; // 학습컨텐츠-태그

  public OutputResultDTO(Long ownerId, Long cycleId, String kpiOkrResultSummary,
      String goalFailureAnalysis, List<ContentResultDTO> contentRow) {
    this.ownerId = ownerId;
    this.cycleId = cycleId;
    this.kpiOkrResultSummary = kpiOkrResultSummary;
    this.goalFailureAnalysis = goalFailureAnalysis;
    this.contentRow = contentRow;
  }

  @Getter
  @NoArgsConstructor
  public static class ContentResultDTO {
    private Long contentId;
    private String reason;

    public ContentResultDTO(Long contentId, String reason) {
      this.contentId = contentId;
      this.reason = reason;
    }
  }
}
