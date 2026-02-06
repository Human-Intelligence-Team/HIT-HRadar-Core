package org.hit.hradar.domain.salary.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.salary.query.dto.request.BasicSalarySearchRequest;
import org.hit.hradar.domain.salary.query.dto.request.SalaryApprovalRequest;
import org.hit.hradar.domain.salary.query.dto.response.BasicSalaryHistoryResponse;
import org.hit.hradar.domain.salary.query.dto.response.BasicSalarySearchResponse;
import org.hit.hradar.domain.salary.query.dto.response.SalaryApprovalResponse;
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

@Tag(name = "Basic Salary Query", description = "기본급 정보 조회 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/basic-salaries")
public class BasicSalaryQueryController {

  private final BasicSalaryQueryService basicSalaryQueryService;

  /**
   * 기본급 결재 목록 조회
   *
   * @return
   */
  @Operation(summary = "기본급목록 조회", description = "년도별로 기본급 내역을 조회합니다.")
  @GetMapping("")
  public ResponseEntity<ApiResponse<SalaryApprovalResponse>> approvedBasicSalaries(
      @CurrentUser AuthUser authUser,
      SalaryApprovalRequest request) {

    Long comId = authUser.companyId();
    System.out.println("$$$$$$ " + request.getYear());
    SalaryApprovalResponse response = basicSalaryQueryService.approvedBasicSalaries(comId, request);
    return ResponseEntity.ok(ApiResponse.success(response));
  }

  /**
   * 연봉 목록 조회(전체)
   * 
   * @return
   */
  @Operation(summary = "연봉 목록 조회(전체)", description = "페이징 및 검색 조건을 적용하여 전체 사원의 연봉 목록을 조회합니다.")
  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<BasicSalarySearchResponse>> basicSalaries(
      @PathVariable("id") Long id,
      @CurrentUser AuthUser authUser,
      BasicSalarySearchRequest request) {

    Long docId = id;
    Long comId = authUser.companyId();
    BasicSalarySearchResponse response = basicSalaryQueryService.basicSalaries(request, docId, comId);
    return ResponseEntity.ok(ApiResponse.success(response));
  }




  /**
   * 연봉 목록 조회(본인)
   * 
   * @return
   */
  @Operation(summary = "연봉 목록 조회(본인)", description = "현재 로그인한 사용자의 연봉 계약 목록을 조회합니다.")
  @GetMapping("/me")
  public ResponseEntity<ApiResponse<BasicSalarySearchResponse>> getMyBasicSalaries(
      @CurrentUser AuthUser authUser,
      SalaryApprovalRequest request) {
    // 사원 ID
    Long empId = authUser.employeeId();
    Long comId = authUser.companyId();
    BasicSalarySearchResponse response = basicSalaryQueryService.getMyBasicSalaries(empId, comId, request);
    return ResponseEntity.ok(ApiResponse.success(response));
  }

  /**
   * 연봉 목록 조회(본인)
   * 
   * @return
   */
  @Operation(summary = "연봉 요약 조회(본인)", description = "특정 연도의 본인 연봉 요약 정보를 조회합니다.")
  @GetMapping("/summary/me")
  public ResponseEntity<ApiResponse<BasicSalaryHistoryResponse>> getMyBasicSalarySummary(
      @CurrentUser AuthUser authUser,
      @RequestParam(name = "year") String year

  ) {

    Long empId = authUser.employeeId();
    BasicSalaryHistoryResponse response = basicSalaryQueryService.getMyBasicSalarySummary(empId, year);
    return ResponseEntity.ok(ApiResponse.success(response));
  }

  /**
   * 사원의 기본급 히스토리
   * 
   * @return
   */
  @Operation(summary = "사원 기본급 히스토리 조회", description = "특정 사원의 기본급 변경 이력을 조회합니다.")
  @GetMapping("/{empId}/history")
  public ResponseEntity<ApiResponse<BasicSalaryHistoryResponse>> getBasicSalaryHistory(
      @PathVariable("empId") Long empId
  ) {

    BasicSalaryHistoryResponse response = basicSalaryQueryService.getBasicSalaryHistory(empId);
    return ResponseEntity.ok(ApiResponse.success(response));
  }

}
