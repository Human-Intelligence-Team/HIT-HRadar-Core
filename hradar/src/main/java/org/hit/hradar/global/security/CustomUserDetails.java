package org.hit.hradar.global.security;

import java.util.Collection;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.user.command.domain.aggregate.AccountStatus;
import org.hit.hradar.domain.user.command.domain.aggregate.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

  private final Long accId;
  private final String loginId;
  private final String password;
  private final UserRole userRole;           // 전역 Role (platform 레벨)
  private final AccountStatus status;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority("ROLE_" + userRole.name()));
  }

  @Override
  public String getPassword() { return password; }

  @Override
  public String getUsername() { return loginId; }

  @Override
  public boolean isEnabled() { return status == AccountStatus.ACTIVE; }

}
