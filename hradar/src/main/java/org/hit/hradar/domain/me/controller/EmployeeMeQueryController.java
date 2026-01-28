package org.hit.hradar.domain.me.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.me.dto.MeResponse;
import org.hit.hradar.domain.me.service.EmployeeMeQueryService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/me")
public class EmployeeMeQueryController {

  private final EmployeeMeQueryService employeeMeQueryService;

  @GetMapping
  public ResponseEntity<ApiResponse<MeResponse>> getMe(
      @CurrentUser AuthUser authUser
  ) {
    MeResponse res = employeeMeQueryService.getMe(authUser);
    return ResponseEntity.ok(ApiResponse.success(res));
  }
}
