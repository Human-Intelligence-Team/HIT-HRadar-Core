package org.hit.hradar.domain.user.command.application.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.user.command.application.dto.request.ChangeMyPasswordRequest;
import org.hit.hradar.domain.user.command.application.service.MyPasswordCommandService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/me")
public class MyPasswordController {

  private final MyPasswordCommandService myPasswordCommandService;

  @PatchMapping("/password")
  public ResponseEntity<ApiResponse<Void>> changeMyPassword(
      @CurrentUser AuthUser authUser,
      @RequestBody ChangeMyPasswordRequest request
  ) {

    myPasswordCommandService.changeMyPassword(authUser.userId(), request);
    return ResponseEntity.ok().build();
  }
}
