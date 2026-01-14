package org.hit.hradar.domain.auth.command.application.dto;

import lombok.Getter;

@Getter
public class ResetPasswordRequest {

  private String resetToken;
  private String newPassword;
}

