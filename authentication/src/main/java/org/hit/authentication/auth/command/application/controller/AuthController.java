package org.hit.authentication.auth.command.application.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.hit.authentication.auth.command.application.dto.AccessTokenResponse;
import org.hit.authentication.auth.command.application.dto.LoginRequest;
import org.hit.authentication.auth.command.application.dto.TokenResponse;
import org.hit.authentication.auth.command.application.service.AuthService;
import org.hit.authentication.common.jwt.JwtTokenProvider;
import org.hit.authentication.common.dto.ApiResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

  private final AuthService authService;
  private final JwtTokenProvider jwtTokenProvider;
  private static final String COOKIE_NAME = "refreshToken";

  @PostMapping("/login")
  public ResponseEntity<ApiResponse<AccessTokenResponse>> login(
      @RequestBody LoginRequest request
  ) {
    TokenResponse response = authService.login(request);
    log.info("TEST LOGBACK");
    ResponseCookie refreshCookie = ResponseCookie.from(COOKIE_NAME, response.getRefreshToken())
        .httpOnly(true)
        .secure(false)   // 개발 환경에서는 false, 운영에서는 true
        .path("/")
        .maxAge(
            jwtTokenProvider.getRefreshExpiration() / 1000)
        .sameSite("Lax")
        .build();

    AccessTokenResponse accessTokenResponse = AccessTokenResponse.builder()
        .accessToken(
            response.getAccessToken())
        .build();
    return ResponseEntity.ok()
        .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
        .body(ApiResponse.success(accessTokenResponse));


  }

  @PostMapping("/logout")
  public ResponseEntity<ApiResponse<Void>> logout(
      @RequestHeader("Authorization") String authorizationHeader
  ) {
    authService.logout(authorizationHeader);

    ResponseCookie deleteCookie = ResponseCookie.from(COOKIE_NAME, "")
        .httpOnly(true)
        .secure(false)
        .path("/")
        .maxAge(0)     // 즉시 만료
        .sameSite("Lax")
        .build();

    return ResponseEntity.ok()
        .header(HttpHeaders.SET_COOKIE, deleteCookie.toString())
        .body(ApiResponse.success(null));


  }

  @PostMapping("/token-reissue")
  public ResponseEntity<ApiResponse<AccessTokenResponse>> tokenReissue(
      @CookieValue(name = COOKIE_NAME, required = false) String refreshToken
  ) {


    AccessTokenResponse newAccessToken = authService.tokenReissue(
        refreshToken
    );

    ResponseCookie refreshCookie = ResponseCookie.from(COOKIE_NAME, refreshToken)
        .httpOnly(true)
        .secure(false)
        .path("/")
        .maxAge(
            jwtTokenProvider.getRefreshExpiration() / 1000)
        .sameSite("Lax")
        .build();

    return ResponseEntity.ok()
        .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
        .body(ApiResponse.success(newAccessToken));


  }

}
