package org.hit.hradar.global.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hit.hradar.domain.user.UserErrorCode;
import org.hit.hradar.domain.user.command.domain.aggregate.UserAccount;
import org.hit.hradar.domain.user.command.domain.repository.UserRepository;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public CustomUserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {

    UserAccount userAccount = userRepository.findByLoginId(loginId)
        .orElseThrow(() -> new BusinessException(UserErrorCode.USER_NOT_FOUND));

    return new CustomUserDetails(
        userAccount.getAccId(),
        userAccount.getLoginId(),
        userAccount.getPassword(),
        userAccount.getUserRole(),
        userAccount.getStatus()
    );
  }
}
