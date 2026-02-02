package org.hit.hradar.domain.user.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.user.command.application.dto.request.ChangeMyPasswordRequest;
import org.hit.hradar.domain.user.command.application.service.MyPasswordCommandService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User Password", description = "사용자 비밀번호 관리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users/me")
public class MyPasswordController {

  private final MyPasswordCommandService myPasswordCommandService;

  @Operation(summary = "비밀번호 변경", description = "현재 로그인한 사용자의 비밀번호를 새 비밀번호로 변경합니다.")
  @PatchMapping("/password")
  public ResponseEntity<ApiResponse<Void>> changeMyPassword(
      @CurrentUser AuthUser authUser,
      @RequestBody ChangeMyPasswordRequest request) {

    myPasswordCommandService.changeMyPassword(authUser.userId(), request);
    return ResponseEntity.ok().build();
  }
}
