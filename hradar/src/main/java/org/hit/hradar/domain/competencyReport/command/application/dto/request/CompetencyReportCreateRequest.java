package org.hit.hradar.domain.competencyReport.command.application.dto.request;

import lombok.Getter;

@Getter
public class CompetencyReportCreateRequest {

  private Long cycleId; // 회차 ID
  private String startDate; // 시작일
  private String endDate; // 종료일

}
