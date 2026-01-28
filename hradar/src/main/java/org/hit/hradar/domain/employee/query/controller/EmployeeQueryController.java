package org.hit.hradar.domain.employee.query.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.employee.query.dto.EmployeeResponse;
import org.hit.hradar.domain.employee.query.service.EmployeeQueryService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeQueryController {

  private final EmployeeQueryService employeeQueryService;

  @GetMapping("/{empId}")
  public ResponseEntity<ApiResponse<EmployeeResponse>> getOne(
      @CurrentUser AuthUser authUser,
      @PathVariable Long empId
  ) {
    EmployeeResponse res = employeeQueryService.getById(authUser.companyId(), empId);
    return ResponseEntity.ok(ApiResponse.success(res));
  }

  @GetMapping
  public ResponseEntity<ApiResponse<List<EmployeeResponse>>> list(
      @CurrentUser AuthUser authUser,
      @RequestParam(required = false) Long deptId,
      @RequestParam(required = false) Long positionId
  ) {
    List<EmployeeResponse> res = employeeQueryService.list(authUser.companyId(), deptId, positionId);
    return ResponseEntity.ok(ApiResponse.success(res));
  }
}
