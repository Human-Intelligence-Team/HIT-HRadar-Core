package org.hit.hradar.domain.employee.command.application.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.employee.command.application.dto.request.UpdateEmployeeAssignmentRequest;
import org.hit.hradar.domain.employee.command.application.dto.reponse.UpdateEmployeeAssignmentResponse;
import org.hit.hradar.domain.employee.command.application.service.EmployeeUpdateApplicationService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeAssignmentCommandController {
  // 인사이동
  private final EmployeeUpdateApplicationService employeeUpdateApplicationService;

  @PatchMapping("/{empId}/assignment")
  public ResponseEntity<ApiResponse<UpdateEmployeeAssignmentResponse>> updateAssignment(
      @CurrentUser AuthUser authUser,
      @PathVariable Long empId,
      @RequestBody UpdateEmployeeAssignmentRequest request
  ) {
    UpdateEmployeeAssignmentResponse response =
        employeeUpdateApplicationService.updateAssignment(authUser.companyId(), authUser.userId(),
            empId, request);

    return ResponseEntity.ok(ApiResponse.success(response));
  }
}
