package org.hit.hradar.domain.employee.query.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.employee.query.dto.EmployeeResponse;
import org.hit.hradar.domain.employee.query.service.EmployeeQueryService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/employees")
public class EmployeeQueryController {

  private final EmployeeQueryService employeeQueryService;

  @GetMapping("/{empId}")
  public ResponseEntity<ApiResponse<EmployeeResponse>> getEmployeeById(
      @PathVariable Long empId,
      @CurrentUser AuthUser authUser
  ) {
    EmployeeResponse response = employeeQueryService.getEmployeeById(empId, authUser.companyId());
    return ResponseEntity.ok(ApiResponse.success(response));
  }

  @GetMapping
  public ResponseEntity<ApiResponse<List<EmployeeResponse>>> getAllEmployeesByCompany(
      @CurrentUser AuthUser authUser
  ) {
    List<EmployeeResponse> responses = employeeQueryService.getAllEmployeesByCompany(authUser.companyId());
    return ResponseEntity.ok(ApiResponse.success(responses));
  }

}
