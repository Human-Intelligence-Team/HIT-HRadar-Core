package org.hit.hradar.domain.user.command.application.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.user.command.application.dto.request.UpdateUserAccountRequest;
import org.hit.hradar.domain.user.command.application.service.UserService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserUpdateController {

  private final UserService userService;

  @PatchMapping("/{accId}")
  public ResponseEntity<ApiResponse<Void>> updateUserAccount(
      @PathVariable Long accId,
      @RequestBody UpdateUserAccountRequest request,
      @CurrentUser AuthUser authUser
  ) {
    userService.updateUserAccount(accId, authUser.companyId(), request);
    return ResponseEntity.ok(ApiResponse.success(null));
  }

}
