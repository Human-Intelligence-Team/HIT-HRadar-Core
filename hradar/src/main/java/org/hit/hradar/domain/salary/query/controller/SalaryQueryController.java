package org.hit.hradar.domain.salary.query.controller;


import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.salary.query.dto.SalaryHistoryDTO;
import org.hit.hradar.domain.salary.query.dto.request.SalarySearchRequest;
import org.hit.hradar.domain.salary.query.dto.response.SalaryHistoryResponse;
import org.hit.hradar.domain.salary.query.dto.response.SalarySearchResponse;
import org.hit.hradar.domain.salary.query.service.SalaryService;
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
@RequestMapping("/salaries")
public class SalaryQueryController {

  private final SalaryService salaryService;
  /**
   * 연봉 목록 조회(전체)
   * @return
   */
  @GetMapping("/all")
  public ResponseEntity<ApiResponse<SalarySearchResponse>> salaries(
      SalarySearchRequest request
  )  {

    SalarySearchResponse response = salaryService.salaries(request);
    return ResponseEntity.ok(ApiResponse.success(response));
  }

  /**
   * 연봉 목록 조회(본인)
   * @return
   */
  @GetMapping("/me")
  public ResponseEntity<ApiResponse<SalarySearchResponse>> getMySalaries()  {

    SalarySearchResponse response = salaryService.getMySalaries();
    return ResponseEntity.ok(ApiResponse.success(response));
  }

  /**
   * 연봉 목록 조회(본인)
   * @return
   */
  @GetMapping("/history/me")
  public ResponseEntity<ApiResponse<SalaryHistoryResponse>> getMySalaryHistory(
      @CurrentUser AuthUser authUser,
      @RequestParam String year) {


    SalaryHistoryResponse response = salaryService.getMySalaryHistory(authUser, year);
    return ResponseEntity.ok(ApiResponse.success(response));
  }

}
