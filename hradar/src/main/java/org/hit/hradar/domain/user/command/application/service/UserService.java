package org.hit.hradar.domain.user.command.application.service;


import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.auth.command.application.dto.ResetPasswordRequest;
import org.hit.hradar.domain.user.UserErrorCode;
import org.hit.hradar.domain.user.command.application.dto.CreateAdminAccountRequest;
import org.hit.hradar.domain.user.command.application.dto.CreateUserAccountRequest;
import org.hit.hradar.domain.user.command.domain.aggregate.AccountStatus;
import org.hit.hradar.domain.user.command.domain.aggregate.UserAccount;
import org.hit.hradar.domain.user.command.domain.aggregate.UserRole;
import org.hit.hradar.domain.user.command.domain.repository.UserRepository;
import org.hit.hradar.global.exception.BusinessException;
import org.hit.hradar.global.util.RandomGenerator;
import org.hit.hradar.global.util.SecurityUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public CreateAdminAccountRequest createAdminAccountRequest(String loginId) {
    if (userRepository.existsByLoginId(loginId)) {
      throw new BusinessException(UserErrorCode.DUPLICATE_LOGIN_ID);
    }
    String tempPw = RandomGenerator.generateTempPassword(12);
    String encoded = passwordEncoder.encode(tempPw);

// encoded로만 저장
    userRepository.save(
        UserAccount.builder()
            .loginId(loginId)
            .password(encoded)
            .status(AccountStatus.ACTIVE)
            .userRole(UserRole.user)
            .build()
    );

// tempPw는 "서면 전달" 용으로만 1회
    return new CreateAdminAccountRequest(saved.getAccId(), saved.getLoginId(), tempPw);
  }
}