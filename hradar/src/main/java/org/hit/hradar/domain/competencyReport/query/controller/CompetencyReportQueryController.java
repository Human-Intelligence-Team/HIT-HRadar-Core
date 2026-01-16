package org.hit.hradar.domain.competencyReport.query.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.competencyReport.query.dto.request.CompetencyReportSearchRequest;
import org.hit.hradar.domain.competencyReport.query.dto.request.CompReportCycleSearchRequest;
import org.hit.hradar.domain.competencyReport.query.dto.response.CompetencyReportSearchResponse;
import org.hit.hradar.domain.competencyReport.query.dto.response.CycleSearchResponse;
import org.hit.hradar.domain.competencyReport.query.service.CompetencyReportQueryService;
import org.hit.hradar.global.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/competency-report")
public class CompetencyReportQueryController {

  private final CompetencyReportQueryService competencyReportQueryService;

/**
 * 역량 강화 목록(사원)
 * @param request
 * @return
 */
  @GetMapping("/member")
  public ResponseEntity<ApiResponse<CompetencyReportSearchResponse>> competencyReport_member(
      @ModelAttribute CompetencyReportSearchRequest request
  )  {

    CompetencyReportSearchResponse response = competencyReportQueryService.getCompetencyReportsByEmployee(request);
    return ResponseEntity.ok(ApiResponse.success(response));
  }

  /**
   * 역량 강화 목록(팀장)
   * @param request
   * @return
   */
  @GetMapping("/leader")
  public ResponseEntity<ApiResponse<CompetencyReportSearchResponse>> competencyReport_leader(
      @ModelAttribute CompetencyReportSearchRequest request
  )  {

    CompetencyReportSearchResponse response = competencyReportQueryService.getCompetencyReportsByLeader(request);
    return ResponseEntity.ok(ApiResponse.success(response));
  }


  /**
   * 역량 강화 회차 목록(인사팀)
   * @param request
   * @return
   */
  @GetMapping("/cycle")
  public ResponseEntity<ApiResponse<CycleSearchResponse>> competencyReport_cycle(
      @ModelAttribute CompReportCycleSearchRequest request
  )  {

    CycleSearchResponse response = competencyReportQueryService.getCycles(request);
    return ResponseEntity.ok(ApiResponse.success(response));
  }

  /**
   * 역량 강화 회차 목록(인사팀)
   * @param request
   * @return
   */
  @GetMapping("/hr")
  public ResponseEntity<ApiResponse<CompetencyReportSearchResponse>> competencyReport_hr(
      @ModelAttribute CompReportCycleSearchRequest request
  )  {

    CompetencyReportSearchResponse response = competencyReportQueryService.getCompetencyReportsByHr(request);
    return ResponseEntity.ok(ApiResponse.success(response));
  }
}
