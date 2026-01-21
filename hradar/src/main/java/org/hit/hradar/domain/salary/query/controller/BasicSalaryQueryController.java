package org.hit.hradar.domain.salary.query.controller;


import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.salary.query.dto.request.BasicSalarySearchRequest;
import org.hit.hradar.domain.salary.query.dto.response.BasicSalaryHistoryResponse;
import org.hit.hradar.domain.salary.query.dto.response.BasicSalarySearchResponse;
import org.hit.hradar.domain.salary.query.service.BasicSalaryQueryService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/basic-salaries")
public class BasicSalaryQueryController {

  private final BasicSalaryQueryService basicSalaryQueryService;
  /**
   * 연봉 목록 조회(전체)
   * @return
   */
  @GetMapping("/all")
  public ResponseEntity<ApiResponse<BasicSalarySearchResponse>> basicSalaries(
      BasicSalarySearchRequest request
  )  {

    BasicSalarySearchResponse response = basicSalaryQueryService.basicSalaries(request);
    return ResponseEntity.ok(ApiResponse.success(response));
  }

  /**
   * 연봉 목록 조회(본인)
   * @return
   */
  @GetMapping("/me")
  public ResponseEntity<ApiResponse<BasicSalarySearchResponse>> getMyBasicSalaries(
      @CurrentUser AuthUser authUser
  )  {
    // 사원 ID
    Long empId = authUser.employeeId();

    BasicSalarySearchResponse response = basicSalaryQueryService.getMyBasicSalaries(empId);
    return ResponseEntity.ok(ApiResponse.success(response));
  }

  /**
   * 연봉 목록 조회(본인)
   * @return
   */
  @GetMapping("/summary/me")
  public ResponseEntity<ApiResponse<BasicSalaryHistoryResponse>> getMyBasicSalarySummary(
      @CurrentUser AuthUser authUser,
      @RequestParam String year) {

    Long empId = authUser.employeeId();
    BasicSalaryHistoryResponse response = basicSalaryQueryService.getMyBasicSalarySummary(empId, year);
    return ResponseEntity.ok(ApiResponse.success(response));
  }

  /**
   * 사원의 기본급 히스토리
   * @return
   */
  @GetMapping("/{empId}/history")
  public ResponseEntity<ApiResponse<BasicSalaryHistoryResponse>> getBasicSalaryHistory(
      @PathVariable Long empId) {

    BasicSalaryHistoryResponse response = basicSalaryQueryService.getBasicSalaryHistory(empId);
    return ResponseEntity.ok(ApiResponse.success(response));
  }

}
