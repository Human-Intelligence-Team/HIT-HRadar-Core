package org.hit.hradar.domain.salary.query.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.salary.query.dto.request.SalaryApprovalTargetRequest;
import org.hit.hradar.domain.salary.query.dto.response.AnnualCompensationSummaryResponse;
import org.hit.hradar.domain.salary.query.dto.response.SalaryApprovalTargetResponse;
import org.hit.hradar.domain.salary.query.service.SalaryQueryService;
import org.hit.hradar.global.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/salaries")
public class SalaryQueryController {

  private final SalaryQueryService salaryQueryService;

  /**
   * 사원 연간 총 연봉 조회
   * @return
   */
  @GetMapping("/{empId}/annual-summary")
  public ResponseEntity<ApiResponse<AnnualCompensationSummaryResponse>> getEmployeeAnnualSalarySummary(
      @PathVariable Long empId)  {

    AnnualCompensationSummaryResponse response = salaryQueryService.getEmployeeAnnualSalarySummary(empId);
    return ResponseEntity.ok(ApiResponse.success(response));
  }

  /**
   * 결재 등록시 대상 목록
   * @return
   */
  @GetMapping("/targets")
  public ResponseEntity<ApiResponse<SalaryApprovalTargetResponse>> getSalaryApprovalTargets(
      SalaryApprovalTargetRequest request
  )  {

    SalaryApprovalTargetResponse response = salaryQueryService.getSalaryApprovalTargets(request);
    return ResponseEntity.ok(ApiResponse.success(response));
  }
}
