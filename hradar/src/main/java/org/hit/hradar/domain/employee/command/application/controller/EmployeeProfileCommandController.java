package org.hit.hradar.domain.employee.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Employee Profile", description = "사원 프로필 및 계정 관리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeProfileCommandController {

  private final EmployeeCommandService employeeCommandService;
  private final EmployeeAccountCommandService employeeAccountCommandService;

  @Operation(summary = "사원 생성 및 계정 발급", description = "새로운 사원 정보를 등록하고 접속 계정을 동시에 생성합니다.")
  @PostMapping
  public ResponseEntity<ApiResponse<CreateEmployeeWithAccountResponse>> createEmployee(
      @RequestBody CreateEmployeeWithAccountRequest request,
      @CurrentUser AuthUser authUser) {
    Long companyId = authUser.companyId();

    CreateEmployeeWithAccountResponse response = employeeAccountCommandService.createEmployeeWithAccount(companyId,
        request);

    return ResponseEntity.ok(ApiResponse.success(response));
  }

  @Operation(summary = "사원 프로필 수정", description = "기존 사원의 프로필 정보(연락처, 상급자 등)를 수정합니다.")
  @PatchMapping("/{empId}/profile")
  public ResponseEntity<ApiResponse<UpdateEmployeeProfileResponse>> updateProfile(
      @PathVariable Long empId,
      @RequestBody UpdateEmployeeProfileRequest request,
      @CurrentUser AuthUser authUser) {
    Long companyId = authUser.companyId();

    UpdateEmployeeProfileResponse response = employeeCommandService.updateProfile(companyId, empId, request);

    return ResponseEntity.ok(ApiResponse.success(response));
  }

  @Operation(summary = "사원 삭제", description = "시스템에서 정해진 사원의 정보를 삭제 처리합니다.")
  @DeleteMapping("/{empId}")
  public ResponseEntity<ApiResponse<Void>> deleteEmployee(
      @PathVariable Long empId,
      @CurrentUser AuthUser authUser) {
    Long companyId = authUser.companyId();

    employeeCommandService.deleteEmployee(empId, companyId);

    return ResponseEntity.ok(ApiResponse.success(null));
  }
}
