package org.hit.hradar.domain.company;

import org.hit.hradar.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public enum CompanyErrorCode implements ErrorCode {
  COMPANY_NOT_FOUND("COMPANY_001", "회사를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
  COMPANY_CODE_DUPLICATE("COMPANY_002", "회사코드 중복", HttpStatus.BAD_REQUEST),
  COMPANY_CODE_ERROR("COMPANY_003", "시스템 오류", HttpStatus.NOT_FOUND),
  COMPANY_DELETED("COMPANY_004", "해당하는 회사는 삭제되었습니다.", HttpStatus.BAD_REQUEST),
  FORBIDDEN("COMPANY_005", "권한이 없습니다.", HttpStatus.FORBIDDEN),
  COMPANY_NAME_NOT_BLANK("COMPANY_006", "회사이름은 비워둘 수 없습니다.", HttpStatus.BAD_REQUEST),
  ;

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
