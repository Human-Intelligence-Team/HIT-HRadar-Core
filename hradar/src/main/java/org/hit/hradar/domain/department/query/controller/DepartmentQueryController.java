package org.hit.hradar.domain.department.query.controller;

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

@RestController
@RequiredArgsConstructor
@RequestMapping("/departments")
public class DepartmentQueryController {

  private final DepartmentQueryService departmentQueryService;

  @GetMapping("/{deptId}")
  public ResponseEntity<ApiResponse<DepartmentResponse>> getDepartmentById(
      @PathVariable Long deptId,
      @CurrentUser AuthUser authUser) {
    DepartmentResponse response = departmentQueryService.getDepartmentById(deptId, authUser.companyId());

    return ResponseEntity.ok(ApiResponse.success(response));
  }

  @GetMapping
  public ResponseEntity<ApiResponse<DepartmentListResponse>> getAllDepartmentsByCompany(
      @CurrentUser AuthUser authUser) {
    DepartmentListResponse response = departmentQueryService.getAllDepartmentsByCompany(authUser.companyId());

    return ResponseEntity.ok(ApiResponse.success(response));
  }

  @GetMapping("/organization-chart")
  public ResponseEntity<ApiResponse<OrganizationChartResponse>> getOrganizationChart(
      @CurrentUser AuthUser authUser) {
    OrganizationChartResponse response = departmentQueryService.getOrganizationChart(authUser.companyId());

    return ResponseEntity.ok(ApiResponse.success(response));
  }

  @GetMapping("/{deptId}/members")
  public ResponseEntity<ApiResponse<EmployeeListResponse>> getDepartmentMembers(
      @PathVariable Long deptId,
      @CurrentUser AuthUser authUser) {
    EmployeeListResponse response = departmentQueryService.getDepartmentMembers(authUser.companyId(), deptId);

    return ResponseEntity.ok(ApiResponse.success(response));
  }
}
