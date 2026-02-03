package org.hit.authentication.auth.command.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hit.authentication.auth.AccountErrorCode;
import org.hit.authentication.auth.AuthErrorCode;
import org.hit.authentication.auth.command.application.dto.AccessTokenResponse;
import org.hit.authentication.auth.command.application.dto.LoginRequest;
import org.hit.authentication.auth.command.application.dto.TokenDTO;
import org.hit.authentication.auth.command.application.dto.TokenResponse;
import org.hit.authentication.auth.command.domain.aggregate.Account;
import org.hit.authentication.auth.command.domain.aggregate.AccountStatus;
import org.hit.authentication.auth.command.domain.repository.AccountRepository;
import org.hit.authentication.common.exception.BusinessException;
import org.hit.authentication.common.jwt.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class AuthService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthRedisService authRedisService;

    @Transactional
    public TokenResponse login(LoginRequest request) {

      Account account = accountRepository
          .findByComCodeAndLoginIdAndIsDeleted(request.getCompanyCode(), request.getLoginId(), 'N')
          .orElseThrow(() -> new BusinessException(AccountErrorCode.ACCOUNT_NOT_FOUND));


      if (!account.getComCode().equals(request.getCompanyCode())) {
            throw new BusinessException(AccountErrorCode.COMPANY_INVALID);
        }

        if (account.getStatus() == AccountStatus.INACTIVE) {
            throw new BusinessException(AccountErrorCode.ACCOUNT_CANCELED);
        }

        if (!passwordEncoder.matches(request.getPassword(), account.getPassword())) {
            throw new BusinessException(AccountErrorCode.ACCOUNT_INVALID_PASSWORD);
        }

        TokenDTO tokenDTO = TokenDTO.builder()
                .userId(account.getAccId())
                .companyId(account.getComId())
                .employeeId(account.getEmpId())
                .role(account.getRole())
                .build();

        String accessToken = jwtTokenProvider.createAccessToken(tokenDTO);
        String refreshToken = jwtTokenProvider.createRefreshToken(tokenDTO);

        authRedisService.saveRefreshToken(account.getAccId(), refreshToken);

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();


    }

    @Transactional
    public void logout(String authorizationHeader) {

        String accessToken = jwtTokenProvider.resolveToken(authorizationHeader);

        Long userId = jwtTokenProvider.getUserIdFromJWT(accessToken);

        Boolean exist = authRedisService.existRefreshTokenByUserId(userId);
        if (Boolean.FALSE.equals(exist)) {
            throw new BusinessException(AuthErrorCode.INVALID_VERIFICATION_TOKEN);
        }

        authRedisService.deleteRefreshTokenByUserId(userId);
    }

    @Transactional
    public AccessTokenResponse tokenReissue(String refreshToken) {

        if (refreshToken == null) {
            throw new BusinessException(AuthErrorCode.INVALID_REFRESH_TOKEN);
        }

        // RefreshToken 검증
        jwtTokenProvider.validateToken(refreshToken);

        // RefreshToken이 Redis에 존재하는지 확인
        Long accId = jwtTokenProvider.getUserIdFromJWT(refreshToken);
        log.info("[REISSUE] accId(from refreshToken)={}", accId);
        Boolean exists = authRedisService.existRefreshTokenByUserId(accId);
        if (Boolean.FALSE.equals(exists)) {
            throw new BusinessException(AuthErrorCode.INVALID_REFRESH_TOKEN);
        }


        Account account = accountRepository.findByAccIdAndIsDeleted(accId, 'N').orElseThrow(
                        () -> new BusinessException(AccountErrorCode.ACCOUNT_NOT_FOUND));

        TokenDTO tokenDTO = TokenDTO.builder()
                .userId(account.getAccId())
                .companyId(account.getComId())
                .employeeId(account.getEmpId())
                .role(account.getRole())
                .build();

        // 새 AccessToken 생성 (RefreshToken은 재사용)
        String newAccessToken = jwtTokenProvider.createAccessToken(tokenDTO);

        // Body에는 AccessToken만 내려줌
        return AccessTokenResponse.builder()
                .accessToken(newAccessToken)
                .build();

    }
}
