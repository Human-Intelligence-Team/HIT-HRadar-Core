package org.hit.hradar.domain.employee.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.employee.query.dto.EmployeeListRequest;
import org.hit.hradar.domain.employee.query.dto.EmployeeListResponse;
import org.hit.hradar.domain.employee.query.dto.EmployeeResponse;
import org.hit.hradar.domain.employee.query.service.EmployeeQueryService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Employee Query", description = "사원 조회 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeQueryController {

  private final EmployeeQueryService employeeQueryService;

  @Operation(summary = "사원 단건 조회", description = "사원 ID로 정보를 조회합니다.")
  @GetMapping("/{empId}")
  public ResponseEntity<ApiResponse<EmployeeResponse>> getOne(
      @CurrentUser AuthUser authUser,
      @PathVariable("empId") Long empId) {
    EmployeeResponse res = employeeQueryService.getById(authUser.companyId(), empId);

    return ResponseEntity.ok(ApiResponse.success(res));
  }

  @Operation(summary = "사원 목록 조회", description = "조건에 따라 사원 목록을 조회합니다.")
  @GetMapping
  public ResponseEntity<ApiResponse<EmployeeListResponse>> list(
      @CurrentUser AuthUser authUser,
      EmployeeListRequest request) {
    Long comId = authUser.companyId(); // authUser에서 가져온 정보

    EmployeeListResponse res = employeeQueryService.list(comId, request);

    return ResponseEntity.ok(ApiResponse.success(res));
  }

}
