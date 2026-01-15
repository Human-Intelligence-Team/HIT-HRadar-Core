package org.hit.authentication.auth.command.application.dto;

import lombok.Getter;

@Getter
public class ResetPasswordRequest {

  private String resetToken;
  private String newPassword;
}

