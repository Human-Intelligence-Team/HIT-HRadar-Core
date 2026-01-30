package org.hit.authentication.auth.command.application.controller;

import lombok.RequiredArgsConstructor;
import org.hit.authentication.auth.command.application.dto.PasswordResetConfirmRequest;
import org.hit.authentication.auth.command.application.dto.PasswordResetRequest;
import org.hit.authentication.auth.command.application.dto.PasswordResetTokenResponse;
import org.hit.authentication.auth.command.application.service.PasswordResetService;
import org.hit.authentication.common.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/password-reset")
public class PasswordResetController {

  private final PasswordResetService passwordResetService;

  @PostMapping("/request")
  public ResponseEntity<ApiResponse<PasswordResetTokenResponse>> request(
      @RequestBody PasswordResetRequest req
  ) {
    String token = passwordResetService.requestResetToken(req);
    return ResponseEntity.ok(ApiResponse.success(new PasswordResetTokenResponse(token)));
  }

  @PostMapping("/confirm")
  public ResponseEntity<ApiResponse<Void>> confirm(
      @RequestBody PasswordResetConfirmRequest req
  ) {
    passwordResetService.confirmReset(req);
    return ResponseEntity.ok(ApiResponse.success(null));
  }

}
