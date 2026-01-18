package org.hit.hradar.domain.attendance;

import lombok.Getter;
import org.hit.hradar.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum AttendanceErrorCode implements ErrorCode {
  ATTENDANCE_NOT_FOUND("ATT_001", "근태 정보를 찾을 수 없습니다." , HttpStatus.NOT_FOUND),
  ATTENDANCE_ALREADY_FOUND("ATT_002", "이미 출근 처리되었습니다." , HttpStatus.NOT_FOUND),
  ATTENDANCE_CHECK_IN_FOUND("ATT_003", "출근 기록이 없습니다." , HttpStatus.NOT_FOUND),
  INVALID_ATTENDANCE_STATE("ATT_004", "이미 자동 근무가 적용 중입니다.", HttpStatus.NOT_FOUND);

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