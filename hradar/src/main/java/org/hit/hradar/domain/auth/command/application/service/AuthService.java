package org.hit.hradar.domain.auth.command.application.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.auth.AuthErrorCode;
import org.hit.hradar.domain.auth.command.application.dto.AccessTokenResponse;
import org.hit.hradar.domain.auth.command.application.dto.LoginRequest;
import org.hit.hradar.domain.auth.command.application.dto.TokenResponse;
import org.hit.hradar.domain.user.UserErrorCode;
import org.hit.hradar.domain.user.command.domain.aggregate.AccountStatus;
import org.hit.hradar.domain.user.command.domain.aggregate.UserAccount;
import org.hit.hradar.domain.user.command.domain.repository.UserRepository;
import org.hit.hradar.global.dto.LoginUserInfo;
import org.hit.hradar.global.exception.BusinessException;
import org.hit.hradar.global.security.jwt.JwtTokenProvider;
import org.hit.hradar.global.util.AuthRedisService;
import org.hit.hradar.global.util.SecurityUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtTokenProvider jwtTokenProvider;
  private final AuthRedisService authRedisService;

  @Transactional
  public TokenResponse login(LoginRequest request) {


    UserAccount userAccount = userRepository.findByLoginId(request.getLoginId())
        .orElseThrow(
            () -> new BusinessException(UserErrorCode.USER_NOT_FOUND));

    AccountStatus status = userAccount.getStatus();

    if (status == AccountStatus.RETIRED) {
      throw new BusinessException(UserErrorCode.USER_CANCELED);
    }

    if (!passwordEncoder.matches(request.getPassword(), userAccount.getPassword())) {
      throw new BusinessException(UserErrorCode.USER_INVALID_PASSWORD);
    }

    String accessToken = jwtTokenProvider.createAccessToken(userAccount.getLoginId(), userAccount.getUserRole());
    String refreshToken = jwtTokenProvider.createRefreshToken(userAccount.getLoginId(), userAccount.getUserRole());

    authRedisService.saveRefreshToken(userAccount.getLoginId(), refreshToken);

    return TokenResponse.builder()
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .build();


  }

  @Transactional
  public void logout(String authorizationHeader) {


    String accessToken = jwtTokenProvider.resolveToken(authorizationHeader);

    LoginUserInfo user = SecurityUtil.getLoginUserInfo();

    Boolean exist = authRedisService.existRefreshTokenByLoginId(user.getLoginId());
    if (Boolean.FALSE.equals(exist)) {
      throw new BusinessException(AuthErrorCode.INVALID_VERIFICATION_TOKEN);
    }

    long remainingTime = jwtTokenProvider.getRemainingTime(accessToken);

    authRedisService.deleteRefreshTokenByLoginId(user.getLoginId());



  }

  @Transactional
  public AccessTokenResponse tokenReissue(String refreshToken) {


    if (refreshToken == null) {
      throw new BusinessException(AuthErrorCode.INVALID_REFRESH_TOKEN);
    }

// RefreshToken 검증
    jwtTokenProvider.validateToken(refreshToken);

// RefreshToken이 Redis에 존재하는지 확인
    String loginId = jwtTokenProvider.getLoginIdFromJWT(refreshToken);

    Boolean exists = authRedisService.existRefreshTokenByLoginId(loginId);
    if (Boolean.FALSE.equals(exists)) {
      throw new BusinessException(AuthErrorCode.INVALID_REFRESH_TOKEN);
    }

// 새 AccessToken 생성 (RefreshToken은 재사용)
    String newAccessToken =
        jwtTokenProvider.createAccessToken(
            loginId,
            jwtTokenProvider.getUserRoleFromJWT(refreshToken)
        );

// Body에는 AccessToken만 내려줌
    return AccessTokenResponse.builder()
        .accessToken(newAccessToken)
        .build();


  }

  public void sendVerificationCode(EmailRequest request) {

    validateEmailRequest(request);

    switch (request.getPurpose()) {
      case SIGNUP -> validateForSignup(request.getEmail());

      case ID_SEARCH, PASSWORD_SEARCH -> validateForSearch(request.getEmail());

      case PASSWORD_RESET -> validateForPasswordReset(request.getEmail());
    }

    String code = EmailVerificationCode.getCode();

    authRedisService.saveVerificationCode(request, code);

    mailService.sendMail(request, MailType.VERIFICATION, code);
  }

  private void validateForSignup(String email) {
    boolean exists = userRepository.existsByEmail(email);
    if (exists) {
      throw new BusinessException(UserErrorCode.EMAIL_ALREADY_EXISTS);
    }
  }

  public VerificationCodeResponse existVerificationCode(VerificationCodeRequest request) {

    Boolean exist = authRedisService.existVerificationCode(request);

    if (Boolean.FALSE.equals(exist)) {
      throw new BusinessException(AuthErrorCode.INVALID_VERIFICATION_TOKEN);
    }

    // 인증코드 1회용 → 즉시 삭제
    authRedisService.deleteKeyEmail(request);

    return switch (request.getPurpose()) {
      case SIGNUP -> {
        authRedisService.markSignupVerified(request.getEmail());
        yield VerificationCodeResponse.builder()
            .success(true)
            .resetToken(null)
            .build();
      }

      case ID_SEARCH, PASSWORD_RESET, PASSWORD_SEARCH -> {
        String resetToken = authRedisService.createAndSaveResetToken(request.getEmail());
        yield VerificationCodeResponse.builder()
            .success(true)
            .resetToken(resetToken)
            .build();
      }

      default -> throw new BusinessException(UserErrorCode.INVALID_PURPOSE);
    };
  }

  private void validateEmailRequest(EmailRequest request) {
    if (request.getEmail() == null || request.getEmail().isBlank()) {
      throw new BusinessException(UserErrorCode.EMAIL_REQUIRED);
    }
    if (request.getPurpose() == null) {
      throw new BusinessException(UserErrorCode.INVALID_PURPOSE);
    }
  }

  private void validateForSearch(String email) {
    userRepository.findByEmail(email)
        .orElseThrow(() -> new BusinessException(UserErrorCode.USER_NOT_FOUND));
  }
}
