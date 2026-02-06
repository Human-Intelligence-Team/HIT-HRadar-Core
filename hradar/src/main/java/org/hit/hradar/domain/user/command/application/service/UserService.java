package org.hit.hradar.domain.user.command.application.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.user.UserErrorCode;
import org.hit.hradar.domain.user.command.application.dto.request.CreateFirstUserRequest;
import org.hit.hradar.domain.user.command.application.dto.request.UpdateUserAccountRequest;
import org.hit.hradar.domain.user.command.domain.aggregate.Account;
import org.hit.hradar.domain.user.command.domain.aggregate.AccountStatus;
import org.hit.hradar.domain.user.command.domain.aggregate.UserRole;
import org.hit.hradar.domain.user.command.domain.repository.AccountRepository;
import org.hit.hradar.domain.user.command.infrastructure.AccountJpaRepository;
import org.hit.hradar.global.client.AuthInternalClient;
import org.hit.hradar.global.exception.BusinessException;
import org.hit.hradar.global.util.RandomGenerator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

  private final AccountRepository accountRepository;
  private final AccountJpaRepository userJpaRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthInternalClient authInternalClient;

  @Transactional
  public CreateFirstUserRequest createFirstUserRequest(
      Long comId,
      String companyCode,
      Long empId,
      String loginId,
      String name,
      String email,
      String password) {
    if (userJpaRepository.existsByComIdAndLoginIdAndStatus(comId, loginId, AccountStatus.ACTIVE)) {
      throw new BusinessException(UserErrorCode.DUPLICATE_LOGIN_ID);
    }
    if (email != null && userJpaRepository.existsByComIdAndEmailAndStatus(comId, email, AccountStatus.ACTIVE)) {
      throw new BusinessException(UserErrorCode.EMAIL_ALREADY_EXISTS);
    }

    String encodedPw = passwordEncoder.encode(password);

    Account saved = accountRepository.save(
        Account.builder()
            .comId(comId)
            .comCode(companyCode)
            .empId(empId)
            .loginId(loginId)
            .name(name)
            .email(email)
            .password(encodedPw)
            .userRole(UserRole.user)
            .status(AccountStatus.ACTIVE)
            .isDeleted('N')
            .build());

    return new CreateFirstUserRequest(
        saved.getAccId(),
        comId,
        companyCode,
        empId,
        loginId,
        password);

  }

  @Transactional
  public void updateUserAccount(Long accId, Long comId, UpdateUserAccountRequest request) {

    Account account = userJpaRepository.findByAccIdAndComIdAndStatus(accId, comId, AccountStatus.ACTIVE)
        .orElseThrow(() -> new BusinessException(UserErrorCode.USER_NOT_FOUND));

    String newLoginId = request.getLoginId();
    String newEmail = request.getEmail();

    if (!account.getLoginId().equals(newLoginId)
        && userJpaRepository.existsByComIdAndLoginIdAndStatus(comId, newLoginId, AccountStatus.ACTIVE)) {
      throw new BusinessException(UserErrorCode.DUPLICATE_LOGIN_ID);
    }

    if (newEmail != null && !newEmail.equals(account.getEmail())
        && userJpaRepository.existsByComIdAndEmailAndStatus(comId, newEmail, AccountStatus.ACTIVE)) {
      throw new BusinessException(UserErrorCode.EMAIL_ALREADY_EXISTS);
    }

    account.updateLoginInfo(newLoginId, request.getName(), newEmail);
  }

  /**
   * 관리자용 비밀번호 초기화
   * - admin: 플랫폼 전역 관리자 (모든 회사 접근 가능)
   * - user: 회사 관리자 (자기 회사만 접근 가능)
   * - 비밀번호를 고정값 "1234"로 초기화
   */
  @Transactional
  public void resetPassword(Long accId, Long managerComId, UserRole managerRole) {
    Account account;

    if (managerRole == UserRole.admin) {
      // 플랫폼 전역 관리자: 회사 제약 없음
      account = userJpaRepository.findByAccIdAndIsDeleted(accId, 'N')
          .orElseThrow(() -> new BusinessException(UserErrorCode.USER_NOT_FOUND));

      // 활성 상태 확인
      if (account.getStatus() != AccountStatus.ACTIVE) {
        throw new BusinessException(UserErrorCode.USER_NOT_FOUND);
      }
    } else {
      // 회사 관리자: 같은 회사 소속인지 확인
      account = userJpaRepository.findByAccIdAndComIdAndStatus(accId, managerComId, AccountStatus.ACTIVE)
          .orElseThrow(() -> new BusinessException(UserErrorCode.USER_NOT_FOUND));
    }

    // 비밀번호 "1234"로 초기화
    String tempPw = "1234";
    account.updatePassword(passwordEncoder.encode(tempPw));

    // 기존 리프레시 토큰 무효화 요청 (동기 - 보안 일관성 유지)
    try {
      authInternalClient.revokeRefreshToken(account.getAccId()).block();
    } catch (Exception e) {
      log.warn("[AUTH_REVOKE_FAIL] accountId={}, message={}", account.getAccId(), e.getMessage());
    }
  }

}
