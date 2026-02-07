package org.hit.hradar.domain.salary.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.salary.query.dto.request.CompensationSearchRequest;
import org.hit.hradar.domain.salary.query.dto.request.CompensationHistorySearchRequest;
import org.hit.hradar.domain.salary.query.dto.request.SalaryApprovalRequest;
import org.hit.hradar.domain.salary.query.dto.response.CompensationSearchResponse;
import org.hit.hradar.domain.salary.query.dto.response.CompensationSummaryResponse;
import org.hit.hradar.domain.salary.query.dto.response.CompensationHistorySearchResponse;
import org.hit.hradar.domain.salary.query.dto.response.SalaryApprovalResponse;
import org.hit.hradar.domain.salary.query.service.CompensationSalaryQueryService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Compensation Salary Query", description = "변동 보상(성과급 등) 정보 조회 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/compensation-salaries")
public class CompensationSalaryQueryController {

  private final CompensationSalaryQueryService compensationSalaryQueryService;

  /**
   * 변동보상 결재 목록 조회
   *
   * @return
   */
  @Operation(summary = "기본급목록 조회", description = "년도별로 기본급 내역을 조회합니다.")
  @GetMapping("")
  public ResponseEntity<ApiResponse<SalaryApprovalResponse>> approvedCompensationSalaries(
      @CurrentUser AuthUser authUser,
      SalaryApprovalRequest request) {

    Long comId = authUser.companyId();
    SalaryApprovalResponse response = compensationSalaryQueryService.approvedCompensationSalaries(comId, request);
    return ResponseEntity.ok(ApiResponse.success(response));
  }

  /**
   * 변동 보상 내역 조회 (전체)
   *
   * @return
   */
  @Operation(summary = "변동 보상 내역 조회(전체)", description = "전체 사원의 변동 보상 지급 내역을 조건에 따라 검색합니다.")
  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<CompensationSearchResponse>> compensationSalaries(
      @PathVariable("id") Long id,
      @CurrentUser AuthUser authUser,
      CompensationSearchRequest request) {

    Long docId = id;
    Long comId = authUser.companyId();


    CompensationSearchResponse response = compensationSalaryQueryService.compensationSalaries(request, docId, comId);
    return ResponseEntity.ok(ApiResponse.success(response));
  }

  /**
   * 변동 보상 히스토리 (본인)
   * 
   * @return
   */
  @Operation(summary = "변동 보상 히스토리(본인)", description = "현재 로그인한 사용자의 변동 보상(인센티브 등) 지급 이력을 조회합니다.")
  @GetMapping("/me/history")
  public ResponseEntity<ApiResponse<CompensationHistorySearchResponse>> getCompensationHistory(
      @CurrentUser AuthUser authUser,
      CompensationHistorySearchRequest request) {

    Long empId = authUser.employeeId();
    Long comId = authUser.companyId();
    CompensationHistorySearchResponse response = compensationSalaryQueryService.getCompensationHistory(empId, request, comId);
    return ResponseEntity.ok(ApiResponse.success(response));
  }

  /**
   * 사원의 변동 보상 히스토리 (전체)
   * 
   * @return
   */
  @Operation(summary = "사원 변동 보상 히스토리(전체)", description = "특정 사원의 변동 보상 지급 이력을 조회합니다.")
  @GetMapping("/{empId}/history")
  public ResponseEntity<ApiResponse<CompensationHistorySearchResponse>> getEmployeeCompensationHistory(
      CompensationHistorySearchRequest request,
      @CurrentUser AuthUser authUser,
      @PathVariable("empId") Long empId) {

    Long comId = authUser.companyId();
    CompensationHistorySearchResponse response = compensationSalaryQueryService.getCompensationHistory(empId, request, comId);
    return ResponseEntity.ok(ApiResponse.success(response));
  }



  /**
   * 변동 보상 총 금액 요약
   * 
   * @return
   */
  @Operation(summary = "변동 보상 총 금액 요약", description = "조건에 해당하는 변동 보상의 총 금액 합계를 조회합니다.")
  @GetMapping("/summary")
  public ResponseEntity<ApiResponse<CompensationSummaryResponse>> getCompensationSalariesSummary(
      @CurrentUser AuthUser authUser,
      CompensationSearchRequest request) {

    Long comId = authUser.companyId();
    CompensationSummaryResponse response = compensationSalaryQueryService.getCompensationSalariesSummary(request, comId);
    return ResponseEntity.ok(ApiResponse.success(response));
  }

}
