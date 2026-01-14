package org.hit.hradar.domain.position;

import org.hit.hradar.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public enum PositionErrorCode implements ErrorCode{
  COMPANY_NOT_FOUND("COMPANY_001","Company not found",HttpStatus.NOT_FOUND);

  private final String errorCode;
  private final String message;
  private final HttpStatusCode httpStatusCode;

  PositionErrorCode(String errorCode, String message, HttpStatusCode httpStatusCode) {
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
