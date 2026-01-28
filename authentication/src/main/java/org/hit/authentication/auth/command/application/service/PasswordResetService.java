package org.hit.authentication.auth.command.application.service;

import java.security.SecureRandom;
import java.util.Base64;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hit.authentication.auth.AccountErrorCode;
import org.hit.authentication.auth.AuthErrorCode;
import org.hit.authentication.auth.command.application.dto.PasswordResetConfirmRequest;
import org.hit.authentication.auth.command.application.dto.PasswordResetRequest;
import org.hit.authentication.auth.command.domain.aggregate.Account;
import org.hit.authentication.auth.command.domain.repository.AccountRepository;
import org.hit.authentication.common.email.EmailService;
import org.hit.authentication.common.exception.BusinessException;
import org.hit.authentication.common.security.PasswordResetTokenStore;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Slf4j
@Service
@RequiredArgsConstructor
public class PasswordResetService {

  private final AccountRepository accountRepository;
  private final PasswordEncoder passwordEncoder;
  private final PasswordResetTokenStore tokenStore;
  private final EmailService emailService;

  private static final int TOKEN_BYTES = 32;      // 256-bit
  private static final long EXPIRY_MINUTES = 15;  // 만료

  @Transactional
  public String requestResetToken(PasswordResetRequest req) {
    // 삭제 아닌 계정만 찾는 버전
    Account account = accountRepository
        .findByLoginIdAndIsDeleted(req.loginId(), 'N')
        .orElseGet(() -> accountRepository.findByLoginId(req.loginId())
            .orElseThrow(() -> new BusinessException(AccountErrorCode.ACCOUNT_NOT_FOUND)));

    if (account.getEmail() == null || account.getEmail().isBlank()) {
      throw new BusinessException(AccountErrorCode.EMAIL_REQUIRED);
    }

    String token = generateToken();

    // Redis: token -> accId 저장
    tokenStore.savePasswordResetToken(token, account.getAccId(), EXPIRY_MINUTES);

    // 이메일 발송
    emailService.sendPasswordResetMail(account.getEmail(), token);

    return token; // dev only (운영에서는 반환하지 말 것)
  }


  @Transactional
  public void confirmReset(PasswordResetConfirmRequest req) {
    Long accId = tokenStore.findAccountIdByPasswordResetToken(req.token());
    if (accId == null) {
      throw new BusinessException(AuthErrorCode.INVALID_RESET_TOKEN);
    }

    Account account = accountRepository.findById(accId)
        .orElseThrow(() -> new BusinessException(AccountErrorCode.ACCOUNT_NOT_FOUND));

    String encoded = passwordEncoder.encode(req.newPassword());
    account.updatePassword(encoded);

    // 1회용 토큰 폐기
    tokenStore.deletePasswordResetToken(req.token());
  }

  private String generateToken() {
    byte[] bytes = new byte[TOKEN_BYTES];
    new SecureRandom().nextBytes(bytes);
    return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
  }
}
