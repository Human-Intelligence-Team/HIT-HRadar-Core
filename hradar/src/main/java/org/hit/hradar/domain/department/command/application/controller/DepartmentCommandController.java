package org.hit.hradar.domain.department.command.application.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.department.command.application.dto.CreateDepartmentRequest;
import org.hit.hradar.domain.department.command.application.dto.UpdateDepartmentRequest;
import org.hit.hradar.domain.department.command.application.service.DepartmentCommandService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/departments")
public class DepartmentCommandController {

  private final DepartmentCommandService departmentCommandService;

  @PostMapping
  public ResponseEntity<ApiResponse<Long>> createDepartment(
      @RequestBody CreateDepartmentRequest request,
      @CurrentUser AuthUser authUser
  ) {
    Long deptId = departmentCommandService.createDepartment(request, authUser.companyId());
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(ApiResponse.success(deptId));
  }

  @PatchMapping("/{deptId}")
  public ResponseEntity<ApiResponse<Void>> updateDepartment(
      @PathVariable Long deptId,
      @RequestBody UpdateDepartmentRequest request,
      @CurrentUser AuthUser authUser
  ) {
    departmentCommandService.updateDepartment(deptId, authUser.companyId(), request);
    return ResponseEntity
        .ok(ApiResponse.success(null));
  }

  @DeleteMapping("/{deptId}")
  public ResponseEntity<ApiResponse<Void>> deleteDepartment(
      @PathVariable Long deptId,
      @CurrentUser AuthUser authUser
  ) {
    departmentCommandService.deleteDepartment(deptId, authUser.companyId());
    return ResponseEntity
        .ok(ApiResponse.success(null));
  }
}
