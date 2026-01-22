package org.hit.hradar.domain.department.query.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.department.query.dto.DepartmentNode;
import org.hit.hradar.domain.department.query.dto.DepartmentResponse;
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
      @CurrentUser AuthUser authUser
  ) {
    DepartmentResponse response = departmentQueryService.getDepartmentById(deptId, authUser.companyId());
    return ResponseEntity.ok(ApiResponse.success(response));
  }

  @GetMapping
  public ResponseEntity<ApiResponse<List<DepartmentResponse>>> getAllDepartmentsByCompany(
      @CurrentUser AuthUser authUser
  ) {
    List<DepartmentResponse> responses =
        departmentQueryService.getAllDepartmentsByCompany(authUser.companyId()); // dto에 담아서 전달 컨트롤러에서 list 생성 금지
    return ResponseEntity.ok(ApiResponse.success(responses));
  }

  @GetMapping("/organization-chart")
  public ResponseEntity<ApiResponse<List<DepartmentNode>>> getOrganizationChart(
      @CurrentUser AuthUser authUser
  ) {
    List<DepartmentNode> organizationChart =
        departmentQueryService.getOrganizationChart(authUser.companyId());
    return ResponseEntity.ok(ApiResponse.success(organizationChart));
  }
}
