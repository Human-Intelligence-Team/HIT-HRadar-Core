package org.hit.hradar.domain.employee.command.application.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.employee.command.application.dto.reponse.CreateEmployeeWithAccountResponse;
import org.hit.hradar.domain.employee.command.application.dto.request.CreateEmployeeWithAccountRequest;
import org.hit.hradar.domain.employee.command.application.dto.request.UpdateEmployeeProfileRequest;
import org.hit.hradar.domain.employee.command.application.dto.reponse.UpdateEmployeeProfileResponse;
import org.hit.hradar.domain.employee.command.application.service.EmployeeAccountCommandService;
import org.hit.hradar.domain.employee.command.application.service.EmployeeCommandService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeProfileCommandController {

  private final EmployeeCommandService employeeCommandService;
  private final EmployeeAccountCommandService employeeAccountCommandService;

  @PostMapping
  public ResponseEntity<ApiResponse<CreateEmployeeWithAccountResponse>> createEmployee(
      @RequestBody CreateEmployeeWithAccountRequest request,
      @CurrentUser AuthUser authUser
  ) {
    Long companyId = authUser.companyId();

    CreateEmployeeWithAccountResponse response =
        employeeAccountCommandService.createEmployeeWithAccount(companyId, request);

    return ResponseEntity.ok(ApiResponse.success(response));
  }

  @PatchMapping("/{empId}/profile")
  public ResponseEntity<ApiResponse<UpdateEmployeeProfileResponse>> updateProfile(
      @PathVariable Long empId,
      @RequestBody UpdateEmployeeProfileRequest request,
      @CurrentUser AuthUser authUser
  ) {
    Long companyId = authUser.companyId();

    UpdateEmployeeProfileResponse response =
        employeeCommandService.updateProfile(companyId, empId, request);

    return ResponseEntity.ok(ApiResponse.success(response));
  }

  @DeleteMapping("/{empId}")
  public ResponseEntity<ApiResponse<Void>> deleteEmployee(
      @PathVariable Long empId,
      @CurrentUser AuthUser authUser
  ) {
    Long companyId = authUser.companyId();

    employeeCommandService.deleteEmployee(empId, companyId);

    return ResponseEntity.ok(ApiResponse.success(null));
  }
}
