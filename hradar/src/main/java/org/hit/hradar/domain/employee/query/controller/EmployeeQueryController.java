package org.hit.hradar.domain.employee.query.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.employee.query.dto.EmployeeListRequest;
import org.hit.hradar.domain.employee.query.dto.EmployeeListResponse;
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
    EmployeeResponse res =
        employeeQueryService.getById(authUser.companyId(), empId);

    return ResponseEntity.ok(ApiResponse.success(res));
  }

  @GetMapping
  public ResponseEntity<ApiResponse<EmployeeListResponse>> list(
      @CurrentUser AuthUser authUser,
      EmployeeListRequest request
  ) {
    Long comId = authUser.companyId(); // authUser에서 가져온 정보

    EmployeeListResponse res = employeeQueryService.list(comId, request);

    return ResponseEntity.ok(ApiResponse.success(res));
  }

}

