package org.hit.hradar.domain.department.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.department.query.dto.DepartmentListResponse;
import org.hit.hradar.domain.employee.query.dto.EmployeeListResponse;
import org.hit.hradar.domain.department.query.dto.DepartmentResponse;
import org.hit.hradar.domain.department.query.dto.OrganizationChartResponse;
import org.hit.hradar.domain.department.query.service.DepartmentQueryService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Department Query", description = "부서 조회 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/departments/query")
public class DepartmentQueryController {

  private final DepartmentQueryService departmentQueryService;

  @Operation(summary = "부서 단건 조회", description = "부서 ID로 상세 정보를 조회합니다.")
  @GetMapping("/{deptId}")
  public ResponseEntity<ApiResponse<DepartmentResponse>> getDepartmentById(
      @PathVariable Long deptId,
      @CurrentUser AuthUser authUser) {
    DepartmentResponse response = departmentQueryService.getDepartmentById(deptId, authUser.companyId());

    return ResponseEntity.ok(ApiResponse.success(response));
  }

  @Operation(summary = "전체 부서 목록 조회", description = "회사 내 모든 부서 목록을 조회합니다.")
  @GetMapping
  public ResponseEntity<ApiResponse<DepartmentListResponse>> getAllDepartmentsByCompany(
      @RequestParam(value = "comId", required = false) Long targetComId,
      @CurrentUser AuthUser authUser) {

    Long searchComId = authUser.companyId();
    // [Super Admin Logic] 타 회사 부서 조회 허용
    if (targetComId != null && "admin".equalsIgnoreCase(authUser.role())) {
      searchComId = targetComId;
    }

    DepartmentListResponse response = departmentQueryService.getAllDepartmentsByCompany(searchComId);

    return ResponseEntity.ok(ApiResponse.success(response));
  }

  @Operation(summary = "조직도 조회", description = "회사의 조직도 정보를 트리 구조로 조회합니다.")
  @GetMapping("/organization-chart")
  public ResponseEntity<ApiResponse<OrganizationChartResponse>> getOrganizationChart(
      @CurrentUser AuthUser authUser) {
    OrganizationChartResponse response = departmentQueryService.getOrganizationChart(authUser.companyId());

    return ResponseEntity.ok(ApiResponse.success(response));
  }

  @Operation(summary = "부서원 목록 조회", description = "특정 부서에 속한 사원 목록을 조회합니다.")
  @GetMapping("/{deptId}/members")
  public ResponseEntity<ApiResponse<EmployeeListResponse>> getDepartmentMembers(
      @PathVariable Long deptId,
      @CurrentUser AuthUser authUser) {
    EmployeeListResponse response = departmentQueryService.getDepartmentMembers(authUser.companyId(), deptId);

    return ResponseEntity.ok(ApiResponse.success(response));
  }
}
