package org.hit.hradar.domain.competencyReport.command.application.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.competencyReport.command.application.dto.request.CompetencyReportCreateRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompetencyReportCommandService {


  /**
   * 역량 강화 리포트 생성
   * @param comId
   */
  public void createReport(Long comId, CompetencyReportCreateRequest request) {

    Long cycleId = request.getCycleId(); // 회차


  }
}
