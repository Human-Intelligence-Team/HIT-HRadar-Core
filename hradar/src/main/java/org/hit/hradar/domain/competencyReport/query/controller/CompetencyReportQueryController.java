package org.hit.hradar.domain.competencyReport.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.competencyReport.query.dto.request.CompetencyReportSearchRequest;
import org.hit.hradar.domain.competencyReport.query.dto.request.CompReportCycleSearchRequest;
import org.hit.hradar.domain.competencyReport.query.dto.response.CompetencyReportDetailResponse;
import org.hit.hradar.domain.competencyReport.query.dto.response.CompetencyReportSearchResponse;
import org.hit.hradar.domain.competencyReport.query.dto.response.CycleSearchResponse;
import org.hit.hradar.domain.competencyReport.query.service.CompetencyReportQueryService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Competency Report Query", description = "역량 리포트 조회 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/competency-report")
public class CompetencyReportQueryController {

  private final CompetencyReportQueryService competencyReportQueryService;

  /**
   * 역량 강화 목록(사원)
   * 
   * @param request
   * @return
   */
  @Operation(summary = "내 역량 리포트 조회", description = "현재 로그인한 사용자의 역량 리포트 목록을 조회합니다.")
  @GetMapping("/me")
  public ResponseEntity<ApiResponse<CompetencyReportSearchResponse>> getMyCompetencyReport(
      @CurrentUser AuthUser authUser,
      CompetencyReportSearchRequest request) {

    Long empId = authUser.employeeId();

    CompetencyReportSearchResponse response = competencyReportQueryService.getMyCompetencyReport(empId, request);
    return ResponseEntity.ok(ApiResponse.success(response));
  }

  /**
   * 역량 강화 목록(부서)
   * 
   * @param request
   * @return
   */
  @Operation(summary = "부서 역량 리포트 조회", description = "부서별 역량 리포트 목록을 조회합니다.")
  @GetMapping("/dept")
  public ResponseEntity<ApiResponse<CompetencyReportSearchResponse>> getCompetencyReportByDeptId(
      @CurrentUser AuthUser authUser,
      CompetencyReportSearchRequest request) {

    Long empId = authUser.employeeId();

    CompetencyReportSearchResponse response = competencyReportQueryService.getCompetencyReportByDeptId(empId, request);
    return ResponseEntity.ok(ApiResponse.success(response));
  }

  /**
   * 역량 강화 회차 목록(회차)
   * 
   * @param request
   * @return
   */
  @Operation(summary = "회차별 역량 리포트 목록 조회", description = "특정 회차의 역량 리포트 목록을 조회합니다.")
  @GetMapping("/cycle")
  public ResponseEntity<ApiResponse<CycleSearchResponse>> getCycles(
      CompReportCycleSearchRequest request) {

    CycleSearchResponse response = competencyReportQueryService.getCycles(request);
    return ResponseEntity.ok(ApiResponse.success(response));
  }

  /**
   * 역량 강화 회차 목록(전체)
   * 
   * @param request
   * @return
   */
  @Operation(summary = "전체 역량 리포트 목록 조회", description = "모든 역량 리포트 목록을 조회합니다.")
  @GetMapping("/all")
  public ResponseEntity<ApiResponse<CompetencyReportSearchResponse>> getCompetencyReportsByAll(
      CompReportCycleSearchRequest request) {

    CompetencyReportSearchResponse response = competencyReportQueryService.getCompetencyReportsByAll(request);
    return ResponseEntity.ok(ApiResponse.success(response));
  }

  /**
   * 역량 강화 회차 목록(상세)
   * 
   * @param id
   * @return
   */
  @Operation(summary = "역량 리포트 상세 조회", description = "리포트 ID로 역량 리포트의 상세 내용을 조회합니다.")
  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<CompetencyReportDetailResponse>> getCompetencyReportsById(
      @PathVariable Long id) {

    CompetencyReportDetailResponse response = competencyReportQueryService.getCompetencyReportsById(id);
    return ResponseEntity.ok(ApiResponse.success(response));
  }

  /**
   * 역량 강화 생성여부 목록
   *
   * @param request
   * @return
   */
  @Operation(summary = "전체 역량 리포트 목록 조회", description = "모든 역량 리포트 목록을 조회합니다.")
  @GetMapping("/generated")
  public ResponseEntity<ApiResponse<CompetencyReportSearchResponse>> getGeneratedCompetencyReports(
      @CurrentUser AuthUser authUser,
      CompReportCycleSearchRequest request) {

    System.out.println("CompetencyReportQueryController.getGeneratedCompetencyReports");

    Long comId =  authUser.companyId();
    CompetencyReportSearchResponse response = competencyReportQueryService.getGeneratedCompetencyReports(request, comId);
   return ResponseEntity.ok(ApiResponse.success(response));
  }


}
