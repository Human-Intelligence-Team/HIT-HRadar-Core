package org.hit.hradar.domain.employee.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.employee.query.dto.EmployeeMovementHistoryListResponse;
import org.hit.hradar.domain.employee.query.service.EmployeeMovementHistoryQueryService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Employee Movement History", description = "사원 인사이동 이력 조회 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeMovementHistoryQueryController {

  private final EmployeeMovementHistoryQueryService historyQueryService;

  @Operation(summary = "사원별 이동 이력 조회", description = "특정 사원의 과거 부서 이동 및 발령 히스토리를 조회합니다.")
  @GetMapping("/{empId}/movement-histories")
  public ResponseEntity<ApiResponse<EmployeeMovementHistoryListResponse>> getMovementHistories(
      @CurrentUser AuthUser authUser,
      @PathVariable Long empId) {
    EmployeeMovementHistoryListResponse response = historyQueryService.getHistory(authUser.companyId(), empId);

    return ResponseEntity.ok(ApiResponse.success(response));
  }

  @Operation(summary = "전체 사원 이동 이력 조회", description = "회사의 모든 사원들에 대한 전반적인 인사이동 히스토리를 조회합니다.")
  @GetMapping("/movement-histories")
  public ResponseEntity<ApiResponse<EmployeeMovementHistoryListResponse>> getAllHistories(
      @CurrentUser AuthUser authUser) {
    EmployeeMovementHistoryListResponse response = historyQueryService.getAllHistories(authUser.companyId());

    return ResponseEntity.ok(ApiResponse.success(response));
  }
}
