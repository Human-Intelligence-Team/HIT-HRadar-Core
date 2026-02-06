package org.hit.hradar.domain.user.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.user.command.application.dto.request.UpdateUserAccountRequest;
import org.hit.hradar.domain.user.command.application.service.UserService;
import org.hit.hradar.domain.user.command.domain.aggregate.UserRole;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(name = "User Account Command", description = "사용자 계정 정보 관리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserUpdateController {

  private final UserService userService;

  @Operation(summary = "계정 정보 업데이트", description = "사용자의 계정 정보(전화번호, 주소, 상태 등)를 수정합니다.")
  @PatchMapping("/{accId}")
  public ResponseEntity<ApiResponse<Void>> updateUserAccount(
      @PathVariable Long accId,
      @RequestBody UpdateUserAccountRequest request,
      @CurrentUser AuthUser authUser) {
    userService.updateUserAccount(accId, authUser.companyId(), request);
    return ResponseEntity.ok(ApiResponse.success(null));
  }

  @Operation(summary = "관리자용 비밀번호 초기화", description = "관리자가 사원의 비밀번호를 초기값(1234)으로 초기화합니다.")
  @PatchMapping("/{accId}/reset-password")
  public ResponseEntity<ApiResponse<Void>> resetPassword(
      @PathVariable Long accId,
      @CurrentUser AuthUser authUser) {
    // role 문자열을 UserRole enum으로 변환
    UserRole userRole = UserRole.valueOf(authUser.role());
    userService.resetPassword(accId, authUser.companyId(), userRole);
    return ResponseEntity.ok(ApiResponse.success(null));
  }

}
