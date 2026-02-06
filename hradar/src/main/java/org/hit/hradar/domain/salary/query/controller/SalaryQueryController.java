package org.hit.hradar.domain.salary.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.salary.query.dto.request.SalaryApprovalRequest;
import org.hit.hradar.domain.salary.query.dto.request.SalaryApprovalTargetRequest;
import org.hit.hradar.domain.salary.query.dto.response.AnnualCompensationSummaryResponse;
import org.hit.hradar.domain.salary.query.dto.response.SalaryApprovalResponse;
import org.hit.hradar.domain.salary.query.dto.response.SalaryApprovalTargetResponse;
import org.hit.hradar.domain.salary.query.service.SalaryQueryService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Salary Query", description = "종합 급여 정보 조회 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/salaries")
public class SalaryQueryController {

  private final SalaryQueryService salaryQueryService;

  /**
   * 사원 연간 총 연봉 조회
   * 
   * @return
   */
  @Operation(summary = "사원 연간 총 연봉 조회", description = "특정 사원의 한 해 동안 지급된 총 기본급 및 변동 보상의 합계를 조회합니다.")
  @GetMapping("/{empId}/annual-summary")
  public ResponseEntity<ApiResponse<AnnualCompensationSummaryResponse>> getEmployeeAnnualSalarySummary(
      @CurrentUser AuthUser authUser,
      @PathVariable("empId") Long empId) {


    Long comId = authUser.companyId();
    AnnualCompensationSummaryResponse response = salaryQueryService.getEmployeeAnnualSalarySummary(empId, comId);
    return ResponseEntity.ok(ApiResponse.success(response));
  }

  /**
   * 결재 등록시 대상 목록
   * 
   * @return
   */
  @Operation(summary = "결재 등록 대상 목록 조회", description = "급여 계약 결재를 등록할 때 선택 가능한 대상 사원 목록을 조회합니다.")
  @GetMapping("/targets")
  public ResponseEntity<ApiResponse<SalaryApprovalTargetResponse>> getSalaryApprovalTargets(
      SalaryApprovalTargetRequest request) {

    SalaryApprovalTargetResponse response = salaryQueryService.getSalaryApprovalTargets(request);
    return ResponseEntity.ok(ApiResponse.success(response));
  }





}
