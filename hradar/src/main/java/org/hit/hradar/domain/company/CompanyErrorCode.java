package org.hit.hradar.domain.company;

import org.hit.hradar.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public enum CompanyErrorCode implements ErrorCode{
  COMPANY_NOT_FOUND("COMPANY_001","회사를 찾을 수 없습니다.",HttpStatus.NOT_FOUND),
  COMPANY_CODE_DUPLICATE("COMPANY_002", "회사코드 중복",HttpStatus.BAD_REQUEST),;

  private final String errorCode;
  private final String message;
  private final HttpStatusCode httpStatusCode;

  CompanyErrorCode(String errorCode, String message, HttpStatusCode httpStatusCode) {
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
