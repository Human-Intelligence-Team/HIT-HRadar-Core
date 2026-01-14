package org.hit.hradar.domain.attendance;

import lombok.Getter;
import org.hit.hradar.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum AttendanceErrorCode implements ErrorCode {

  INVALID_IP("ATTENDANCE_001", "접근 가능한 IP가 아닙니다.", HttpStatus.NOT_FOUND),
  ALREADY_CLOSED("ATTENDANCE_002", "이미 출퇴근이 완료된 상태입니다.", HttpStatus.NOT_FOUND);

  private final String errorCode;
  private final String message;
  private final HttpStatus httpStatus;

  AttendanceErrorCode(String errorCode, String message, HttpStatus httpStatus) {
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
  public HttpStatusCode getHttpStatusCode() {
    return httpStatus;
  }
}