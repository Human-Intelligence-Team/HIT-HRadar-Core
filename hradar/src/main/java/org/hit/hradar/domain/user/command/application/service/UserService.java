package org.hit.hradar.domain.user.command.application.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.user.UserErrorCode;
import org.hit.hradar.domain.user.command.application.dto.CreateFirstUserResponse;
import org.hit.hradar.domain.user.command.application.dto.CreateAccountRequest;
import org.hit.hradar.domain.user.command.application.dto.UpdateUserAccountRequest;
import org.hit.hradar.domain.user.command.domain.aggregate.AccountStatus;
import org.hit.hradar.domain.user.command.domain.aggregate.UserRole;
import org.hit.hradar.domain.user.command.domain.aggregate.Account;
import org.hit.hradar.domain.user.command.domain.repository.UserRepository;
import org.hit.hradar.domain.user.command.infrastructure.UserJpaRepository;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;


@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final UserJpaRepository userJpaRepository;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  public CreateFirstUserResponse createFirstUserResponse(
      Long comId,
      String companyCode,
      Long empId,
      String loginId,
      String name,
      String email,
      String password
  ) {
    if (userJpaRepository.existsByComIdAndLoginIdAndStatus(comId, loginId, AccountStatus.ACTIVE)) {
      throw new BusinessException(UserErrorCode.DUPLICATE_LOGIN_ID);
    }
    if (email != null && userJpaRepository.existsByComIdAndEmailAndStatus(comId, email, AccountStatus.ACTIVE)) {
      throw new BusinessException(UserErrorCode.EMAIL_ALREADY_EXISTS);
    }

    String encodedPw = passwordEncoder.encode(password);

    Account saved = userRepository.save(
        Account.builder()
            .comId(comId)
            .comCode(companyCode)
            .empId(empId)
            .loginId(loginId)
            .name(name)
            .email(email)
            .password(encodedPw)
            .userRole(UserRole.user)
            .build()
    );

    return new CreateFirstUserResponse(saved.getAccId(), empId, loginId, comId,
        companyCode, password);
  }

  @Transactional
  public Long createUserAccount(CreateAccountRequest request, Long comId) {
    if (userJpaRepository.existsByComIdAndLoginIdAndStatus(comId, request.getLoginId(), AccountStatus.ACTIVE)) {
      throw new BusinessException(UserErrorCode.DUPLICATE_LOGIN_ID);
    }
    if (request.getEmail() != null && userJpaRepository.existsByComIdAndEmailAndStatus(comId, request.getEmail(), AccountStatus.ACTIVE)) {
      throw new BusinessException(UserErrorCode.EMAIL_ALREADY_EXISTS);
    }

    String encodedPw = passwordEncoder.encode(request.getPassword());

    Account saved = userRepository.save(
        Account.builder()
            .comId(comId)
            .loginId(request.getLoginId())
            .name(request.getName())
            .email(request.getEmail())
            .password(encodedPw)
            .userRole(request.getUserRole())
            .build()
    );
    return saved.getAccId();
  }

  @Transactional
  public void updateUserAccount(Long accId, Long comId, UpdateUserAccountRequest request) {
    Account account = userJpaRepository.findByAccIdAndComIdAndStatus(accId, comId, AccountStatus.ACTIVE)
        .orElseThrow(() -> new BusinessException(UserErrorCode.USER_NOT_FOUND));

    if (!account.getLoginId().equals(request.getLoginId()) &&
        userJpaRepository.existsByComIdAndLoginIdAndStatus(comId, request.getLoginId(), AccountStatus.ACTIVE)) {
      throw new BusinessException(UserErrorCode.DUPLICATE_LOGIN_ID);
    }
    if (request.getEmail() != null && !account.getEmail().equals(request.getEmail()) &&
        userJpaRepository.existsByComIdAndEmailAndStatus(comId, request.getEmail(), AccountStatus.ACTIVE)) {
      throw new BusinessException(UserErrorCode.EMAIL_ALREADY_EXISTS);
    }

    account.updateLoginInfo(request.getLoginId(), request.getName(), request.getEmail());
    account.updateAccountStatus(request.getStatus());
  }

  @Transactional
  public void changePassword(Long accId, Long comId, String oldPassword, String newPassword) {
    Account account = userJpaRepository.findByAccIdAndComIdAndStatus(accId, comId, AccountStatus.ACTIVE)
        .orElseThrow(() -> new BusinessException(UserErrorCode.USER_NOT_FOUND));

    if (!passwordEncoder.matches(oldPassword, account.getPassword())) {
      throw new BusinessException(UserErrorCode.USER_INVALID_PASSWORD);
    }

    account.updatePassword(passwordEncoder.encode(newPassword));
  }

  @Transactional
  public void deleteUserAccount(Long accId, Long comId) {
    Account account = userJpaRepository.findByAccIdAndComIdAndStatus(accId, comId, AccountStatus.ACTIVE)
        .orElseThrow(() -> new BusinessException(UserErrorCode.USER_NOT_FOUND));
    account.markAsDeleted();
  }

  /** 플랫폼 관리자만 승인/조회 가능하게 가드 (임시) */
  @Transactional(readOnly = true)
  public void requirePlatformAdmin(Long accId) {
    Account account = userJpaRepository.findById(accId)
        .orElseThrow(() -> new BusinessException(UserErrorCode.USER_NOT_FOUND));

    if (account.getUserRole() != UserRole.admin) {
      throw new BusinessException(UserErrorCode.FORBIDDEN);
    }
  }
}
