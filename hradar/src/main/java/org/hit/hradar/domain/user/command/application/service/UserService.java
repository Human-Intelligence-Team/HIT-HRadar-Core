package org.hit.hradar.domain.user.command.application.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.user.UserErrorCode;
import org.hit.hradar.domain.user.command.application.dto.request.CreateFirstUserRequest;
import org.hit.hradar.domain.user.command.application.dto.request.CreateAccountRequest;
import org.hit.hradar.domain.user.command.application.dto.request.UpdateUserAccountRequest;
import org.hit.hradar.domain.user.command.domain.aggregate.Account;
import org.hit.hradar.domain.user.command.domain.aggregate.AccountStatus;
import org.hit.hradar.domain.user.command.domain.aggregate.UserRole;
import org.hit.hradar.domain.user.command.domain.repository.AccountRepository;
import org.hit.hradar.domain.user.command.infrastructure.AccountJpaRepository;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class UserService {

  private final AccountRepository accountRepository;
  private final AccountJpaRepository userJpaRepository;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  public CreateFirstUserRequest createFirstUserRequest(
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
            .build()
    );

    return new CreateFirstUserRequest(
        saved.getAccId(),
        comId,
        companyCode,
        empId,
        loginId,
        password
    );


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

    Account saved = accountRepository.save(
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
  public void deleteUserAccount(Long accId, Long comId) {
    Account account = userJpaRepository.findByAccIdAndComIdAndStatus(accId, comId, AccountStatus.ACTIVE)
        .orElseThrow(() -> new BusinessException(UserErrorCode.USER_NOT_FOUND));
    account.isDeleted();
  }


}
