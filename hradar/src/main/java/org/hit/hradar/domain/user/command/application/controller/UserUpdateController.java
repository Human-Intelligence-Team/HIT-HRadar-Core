package org.hit.hradar.domain.user.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.user.command.application.dto.request.UpdateUserAccountRequest;
import org.hit.hradar.domain.user.command.application.service.UserService;
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

}
