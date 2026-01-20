package org.hit.hradar.domain.user;

import org.hit.hradar.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public enum UserErrorCode implements ErrorCode {
  USER_NOT_FOUND("USER_001", "User not found", HttpStatus.NOT_FOUND),
  USER_DUPLICATED("USER_002", "User duplicated", HttpStatus.BAD_REQUEST),
  USER_INVALID_PASSWORD("USER_003", "User invalid password", HttpStatus.BAD_REQUEST),
  USER_CANCELED("USER_004", "User canceled", HttpStatus.NOT_ACCEPTABLE),
  DUPLICATE_LOGIN_ID("USER_005","User Id duplicated", HttpStatus.BAD_REQUEST),
  EMAIL_REQUIRED("EMAIL_001", "Email required" , HttpStatus.BAD_REQUEST),
  INVALID_PURPOSE("EMAIL_002", "Purpose required" , HttpStatus.BAD_REQUEST),
  EMAIL_ALREADY_EXISTS("EMAIL_003", "Email already exists", HttpStatus.BAD_REQUEST),
  ACCOUNT_RETIRED("USER_006", "Account is retired.", HttpStatus.FORBIDDEN), // Added
  FORBIDDEN("USER_007", "Access is forbidden.", HttpStatus.FORBIDDEN), // Added

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
