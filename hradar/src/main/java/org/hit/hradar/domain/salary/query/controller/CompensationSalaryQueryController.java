package org.hit.hradar.domain.salary.query.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.salary.query.dto.request.CompensationSearchRequest;
import org.hit.hradar.domain.salary.query.dto.request.CompensationHistorySearchRequest;
import org.hit.hradar.domain.salary.query.dto.response.CompensationSearchResponse;
import org.hit.hradar.domain.salary.query.dto.response.CompensationSummaryResponse;
import org.hit.hradar.domain.salary.query.dto.response.CompensationHistorySearchResponse;
import org.hit.hradar.domain.salary.query.service.CompensationSalaryQueryService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/compensation-salaries")
public class CompensationSalaryQueryController {

  private final CompensationSalaryQueryService compensationSalaryQueryService;

  /**
   * 변동 보상 히스토리 (본인)
   * @return
   */
  @GetMapping("/me/history")
  public ResponseEntity<ApiResponse<CompensationHistorySearchResponse>> getCompensationHistory(
      @CurrentUser AuthUser authUser,
      CompensationHistorySearchRequest request
  )  {

    Long empId = authUser.employeeId();
    CompensationHistorySearchResponse response = compensationSalaryQueryService.getCompensationHistory(empId, request);
    return ResponseEntity.ok(ApiResponse.success(response));
  }

  /**
   * 사원의 변동 보상 히스토리 (전체)
   * @return
   */
  @GetMapping("/{empId}/history")
  public ResponseEntity<ApiResponse<CompensationHistorySearchResponse>> getEmployeeCompensationHistory(
      CompensationHistorySearchRequest request,
      @PathVariable Long empId
  )  {

    CompensationHistorySearchResponse response = compensationSalaryQueryService.getCompensationHistory(empId, request);
    return ResponseEntity.ok(ApiResponse.success(response));
  }

  /**
   * 변동 보상 내역 조회 (전체)
   * @return
   */
  @GetMapping("/all")
  public ResponseEntity<ApiResponse<CompensationSearchResponse>> compensationSalaries(
      CompensationSearchRequest request
  )  {

    CompensationSearchResponse response = compensationSalaryQueryService.compensationSalaries(request);
    return ResponseEntity.ok(ApiResponse.success(response));
  }

  /**
   * 변동 보상 총 금액 요약
   * @return
   */
  @GetMapping("/summary")
  public ResponseEntity<ApiResponse<CompensationSummaryResponse>> getCompensationSalariesSummary(
      CompensationSearchRequest request
  )  {

    CompensationSummaryResponse response = compensationSalaryQueryService.getCompensationSalariesSummary(request);
    return ResponseEntity.ok(ApiResponse.success(response));
  }

}
