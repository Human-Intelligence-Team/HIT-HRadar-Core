package org.hit.hradar.domain.leave.command.application.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.leave.command.application.dto.request.LeavePolicyCreateRequest;
import org.hit.hradar.domain.leave.command.application.service.LeavePolicyCommandService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/leave-policies")
public class LeavePolicyCommandController {

  private final LeavePolicyCommandService leavePolicyCommandService;

  @PostMapping
  public ApiResponse<Void> create(
      @CurrentUser AuthUser authUser,
      @RequestBody LeavePolicyCreateRequest request
  ) {
    leavePolicyCommandService.create(
        authUser.companyId(),
        request
    );
    return ApiResponse.success(null);
  }
}