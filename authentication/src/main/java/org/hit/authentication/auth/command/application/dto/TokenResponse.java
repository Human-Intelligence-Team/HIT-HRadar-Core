package org.hit.authentication.auth.command.application.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenResponse {

  private String accessToken;
  private String refreshToken;
}
