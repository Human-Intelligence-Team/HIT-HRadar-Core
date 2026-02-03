package org.hit.authentication.auth;

import org.hit.authentication.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public enum AuthErrorCode implements ErrorCode {
  INVALID_VERIFICATION_TOKEN("AUTH_001", "토큰을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
  INVALID_REFRESH_TOKEN("AUTH_002", "유효하지 않은 토큰입니다.", HttpStatus.UNAUTHORIZED),
  INVALID_ACCESS_TOKEN("AUTH_003", "유효하지 않은 접근 토큰입니다.", HttpStatus.UNAUTHORIZED),
  ACCESS_TOKEN_NOT_EXPIRED("AUTH_004", "접근 토큰이 만료되었습니다.", HttpStatus.UNAUTHORIZED),
  UNAUTHORIZED_USER("AUTH_005", "접근 권한이 없는 사용자입니다.", HttpStatus.UNAUTHORIZED),
  INVALID_VERIFICATION_CODE("AUTH_006", "유효하지 않은 인증코드입니다.", HttpStatus.BAD_REQUEST),
  INVALID_RESET_TOKEN("AUTH_007", "유효하지 않은 초기화 토큰입니다.", HttpStatus.UNAUTHORIZED);


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
