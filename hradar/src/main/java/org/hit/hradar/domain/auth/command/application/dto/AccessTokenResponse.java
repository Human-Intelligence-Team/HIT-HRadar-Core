package org.hit.hradar.domain.auth.command.application.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AccessTokenResponse {

  private String accessToken;
}
