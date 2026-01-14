package org.hit.hradar.global.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hit.hradar.domain.user.command.domain.aggregate.UserRole;

@Getter
@Builder
@AllArgsConstructor
public class LoginUserInfo {

  private final Long userId;
  private final String loginId;
  private final UserRole userRole;
}