package org.hit.hradar.domain.employee.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.employee.command.application.dto.request.UpdateEmployeeAssignmentRequest;
import org.hit.hradar.domain.employee.command.application.dto.reponse.UpdateEmployeeAssignmentResponse;
import org.hit.hradar.domain.employee.command.application.service.EmployeeUpdateApplicationService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Employee Assignment", description = "사원 인사이동(배정) 관리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeAssignmentCommandController {
  // 인사이동
  private final EmployeeUpdateApplicationService employeeUpdateApplicationService;

  @Operation(summary = "사원 인사이동 수정", description = "특정 사원의 부서, 직위 등 배정 정보를 수정합니다.")
  @PatchMapping("/{empId}/assignment")
  public ResponseEntity<ApiResponse<UpdateEmployeeAssignmentResponse>> updateAssignment(
      @CurrentUser AuthUser authUser,
      @PathVariable Long empId,
      @RequestBody UpdateEmployeeAssignmentRequest request) {
    UpdateEmployeeAssignmentResponse response = employeeUpdateApplicationService.updateAssignment(authUser.companyId(),
        authUser.userId(),
        empId, request);

    return ResponseEntity.ok(ApiResponse.success(response));
  }
}
