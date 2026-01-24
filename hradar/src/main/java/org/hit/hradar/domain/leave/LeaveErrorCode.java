package org.hit.hradar.domain.leave;

import lombok.Getter;
import org.hit.hradar.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;

@Getter
public enum LeaveErrorCode implements ErrorCode {

    LEAVE_OVERLAP("LEAVE_001", "이미 해당 기간에 휴가가 존재합니다.", HttpStatus.FOUND),
    LEAVE_NOT_ENOUGH("LEAVE_002","잔여 연차가 부족합니다.", HttpStatus.BAD_REQUEST),
    LEAVE_NOT_FOUND("LEAVE_003", "결재된 휴가 정보를 찾을 수 없습니다.", HttpStatus.INTERNAL_SERVER_ERROR);


    private final String errorCode;
    private final String message;
    private final HttpStatus httpStatus;

    LeaveErrorCode(String errorCode, String message, HttpStatus httpStatus) {
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

