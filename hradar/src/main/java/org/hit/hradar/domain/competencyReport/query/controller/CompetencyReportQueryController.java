package org.hit.hradar.domain.competencyReport.query.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.competencyReport.query.dto.request.CompetencyReportSearchRequest;
import org.hit.hradar.domain.competencyReport.query.dto.request.CompReportCycleSearchRequest;
import org.hit.hradar.domain.competencyReport.query.dto.response.CompetencyReportDetailResponse;
import org.hit.hradar.domain.competencyReport.query.dto.response.CompetencyReportSearchResponse;
import org.hit.hradar.domain.competencyReport.query.dto.response.CycleSearchResponse;
import org.hit.hradar.domain.competencyReport.query.service.CompetencyReportQueryService;
import org.hit.hradar.global.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/competency-report")
public class CompetencyReportQueryController {

  private final CompetencyReportQueryService competencyReportQueryService;

/**
 * 역량 강화 목록(사원)
 * @param request
 * @return
 */
  @GetMapping("/me")
  public ResponseEntity<ApiResponse<CompetencyReportSearchResponse>> getMyCompetencyReport(
      CompetencyReportSearchRequest request
  )  {

    CompetencyReportSearchResponse response = competencyReportQueryService.getMyCompetencyReport(request);
    return ResponseEntity.ok(ApiResponse.success(response));
  }

  /**
   * 역량 강화 목록(부서)
   * @param request
   * @return
   */
  @GetMapping("/dept")
  public ResponseEntity<ApiResponse<CompetencyReportSearchResponse>> getCompetencyReportByDeptId(
      CompetencyReportSearchRequest request
  )  {

    CompetencyReportSearchResponse response = competencyReportQueryService.getCompetencyReportByDeptId(request);
    return ResponseEntity.ok(ApiResponse.success(response));
  }


  /**
   * 역량 강화 회차 목록(회차)
   * @param request
   * @return
   */
  @GetMapping("/cycle")
  public ResponseEntity<ApiResponse<CycleSearchResponse>> getCycles(
      CompReportCycleSearchRequest request
  )  {

    CycleSearchResponse response = competencyReportQueryService.getCycles(request);
    return ResponseEntity.ok(ApiResponse.success(response));
  }

  /**
   * 역량 강화 회차 목록(전체)
   * @param request
   * @return
   */
  @GetMapping("/all")
  public ResponseEntity<ApiResponse<CompetencyReportSearchResponse>> getCompetencyReportsByAll(
      CompReportCycleSearchRequest request
  )  {

    CompetencyReportSearchResponse response = competencyReportQueryService.getCompetencyReportsByAll(request);
    return ResponseEntity.ok(ApiResponse.success(response));
  }

  /**
   * 역량 강화 회차 목록(상세)
   * @param id
   * @return
   */
  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<CompetencyReportDetailResponse>> getCompetencyReportsById(
      @PathVariable Long id
  )  {

    CompetencyReportDetailResponse response = competencyReportQueryService.getCompetencyReportsById(id);
    return ResponseEntity.ok(ApiResponse.success(response));
  }


}
