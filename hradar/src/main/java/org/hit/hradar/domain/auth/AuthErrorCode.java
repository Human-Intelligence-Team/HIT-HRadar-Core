package org.hit.hradar.domain.auth;

import org.hit.hradar.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public enum AuthErrorCode implements ErrorCode {
  INVALID_VERIFICATION_TOKEN("AUTH_001", "Token not found", HttpStatus.NOT_FOUND),
  INVALID_REFRESH_TOKEN("AUTH_002", "Invalid refresh token", HttpStatus.UNAUTHORIZED),
  INVALID_ACCESS_TOKEN("AUTH_003", "Invalid access token", HttpStatus.UNAUTHORIZED),
  ACCESS_TOKEN_NOT_EXPIRED("AUTH_004", "Access token not expired", HttpStatus.UNAUTHORIZED),
  UNAUTHORIZED_USER("AUTH_005", "Unauthorized user", HttpStatus.UNAUTHORIZED),
  INVALID_VERIFICATION_CODE("AUTH_006", "Invalid verification code", HttpStatus.BAD_REQUEST),
  INVALID_RESET_TOKEN("AUTH_007", "Invalid reset token", HttpStatus.UNAUTHORIZED);


  private final String errorCode;
  private final String message;
  private final HttpStatusCode httpStatusCode;

  AuthErrorCode(String errorCode, String message, HttpStatusCode httpStatusCode) {
    this.errorCode = errorCode;
    this.message = message;
    this.httpStatusCode = httpStatusCode;
  }

  @Override
  public String getErrorCode() {
    return errorCode;
  }

  @Override
  public String getMessage() {
    return message;
  }

  @Override
  public HttpStatusCode getHttpStatusCode() {
    return httpStatusCode;
  }
}
