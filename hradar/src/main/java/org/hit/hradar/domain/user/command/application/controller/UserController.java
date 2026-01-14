package org.hit.hradar.domain.user.command.application.controller;


import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.auth.command.application.dto.ResetPasswordRequest;
import org.hit.hradar.domain.user.command.application.dto.CreateUserAccountRequest;
import org.hit.hradar.domain.user.command.application.service.UserService;
import org.hit.hradar.global.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

  private final UserService userService;

  @PostMapping
  public ResponseEntity<ApiResponse<Void>> registerUser(
      @RequestBody CreateUserAccountRequest request
  ) {
    userService.registerUser(request);
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(ApiResponse.success(null));
  }


  @PostMapping("/reset-password")
  public ResponseEntity<ApiResponse<?>> resetPassword(
      @RequestBody ResetPasswordRequest request
  ) {
    userService.resetPassword(request);
    return ResponseEntity.ok(ApiResponse.success(null));
  }


  @DeleteMapping
  public ResponseEntity<ApiResponse<Void>> deleteUser() {
    userService.deleteUser();
    return ResponseEntity.ok(ApiResponse.success(null));
  }


}
