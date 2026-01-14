package org.hit.hradar.domain.employee;


import org.hit.hradar.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum EmployeeErrorCode implements ErrorCode {
  EMPLOYEE_NOT_FOUND("EMPLOYEE_001", "해당 사원을 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
  private final String errorCode;
  private final String message;
  private final HttpStatus httpStatus;

  EmployeeErrorCode(String errorCode, String message, HttpStatus httpStatus) {
    this.errorCode = errorCode;
    this.message = message;
    this.httpStatus = httpStatus;
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
  public HttpStatus getHttpStatusCode() {
    return httpStatus;
  }
}
