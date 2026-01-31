package org.hit.hradar.domain.user;

import org.hit.hradar.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public enum UserErrorCode implements ErrorCode {
  USER_NOT_FOUND("USER_001", "회원이 존재하지 않습니다", HttpStatus.NOT_FOUND),
  USER_DUPLICATED("USER_002", "같은 회원이 이미 존재합니다", HttpStatus.BAD_REQUEST),
  PASSWORD_DUPLICATED("USER_003", "같은 비빌번호가 존재합니다", HttpStatus.BAD_REQUEST),
  DUPLICATE_LOGIN_ID("USER_005","해당 ID가 이미 존재합니다", HttpStatus.BAD_REQUEST),
  EMAIL_REQUIRED("EMAIL_001", "이메일이 필요합니다" , HttpStatus.BAD_REQUEST),
  EMAIL_ALREADY_EXISTS("EMAIL_002", "같은 이메일이 존재합니다", HttpStatus.BAD_REQUEST),
  FORBIDDEN("USER_007", "접근 권한이 없습니다", HttpStatus.FORBIDDEN),
  INVALID_LOGIN_ID("USER_008","로그인id가 존재하지 않습니다", HttpStatus.BAD_REQUEST)
  ;
  private final String errorCode;
  private final String message;
  private final HttpStatusCode httpStatusCode;

  UserErrorCode(String errorCode, String message, HttpStatusCode httpStatusCode) {
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
