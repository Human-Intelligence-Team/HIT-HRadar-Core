package org.hit.hradar.domain.user.command.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hit.hradar.domain.user.command.application.dto.request.ChangeMyPasswordRequest;
import org.hit.hradar.domain.user.command.domain.aggregate.Account;
import org.hit.hradar.domain.user.command.domain.repository.AccountRepository;
import org.hit.hradar.global.client.AuthInternalClient;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyPasswordCommandService {

  private final AccountRepository accountRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthInternalClient authInternalClient;

  @Transactional
  public void changeMyPassword(Long accId, ChangeMyPasswordRequest request) {

    Account account = accountRepository.findByAccIdAndIsDeleted(accId, 'N')
        .orElseThrow(() -> new IllegalArgumentException("ACCOUNT_NOT_FOUND"));

    if (!passwordEncoder.matches(request.currentPassword(), account.getPassword())) {
      throw new IllegalArgumentException("INVALID_PASSWORD");
    }

    account.updatePassword(passwordEncoder.encode(request.newPassword()));

    try {
      authInternalClient.revokeRefreshToken(accId).block();
    } catch (Exception e) {
      log.warn("[AUTH_REVOKE_FAIL] accId={}, msg={}", accId, e.getMessage(), e);
    }
  }
}
