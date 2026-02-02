package org.hit.hradar.domain.leave.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Leave Policy Command", description = "회사 휴가 정책 생성 관리 API (Admin 전용)")
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/leave-policies")
public class LeavePolicyCommandController {

  private final LeavePolicyCommandService leavePolicyCommandService;

  @Operation(summary = "새 휴가 정책 생성", description = "회사에서 운영할 새로운 휴가 항목(포상휴가, 보건휴가 등)과 발생 조건을 정의합니다.")
  @PostMapping
  public ApiResponse<Void> create(
      @CurrentUser AuthUser authUser,
      @RequestBody LeavePolicyCreateRequest request) {
    leavePolicyCommandService.create(
        authUser.companyId(),
        request);
    return ApiResponse.success(null);
  }
}