package org.hit.hradar.domain.leave;

import lombok.Getter;
import org.hit.hradar.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;

@Getter
public enum LeaveErrorCode implements ErrorCode {

  LEAVE_OVERLAP("LEAVE_001", "이미 해당 기간에 휴가가 존재합니다.", HttpStatus.CONFLICT),
  LEAVE_NOT_ENOUGH("LEAVE_002", "잔여 연차가 부족합니다.", HttpStatus.BAD_REQUEST),
  LEAVE_ALREADY_APPLIED("LEAVE_003", "이미 해당 결재 문서로 휴가가 신청되었습니다.", HttpStatus.CONFLICT),
  LEAVE_NOT_FOUND("LEAVE_004", "휴가 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
  LEAVE_GRANT_NOT_FOUND("LEAVE_005", "연차 지급 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
  LEAVE_POLICY_NOT_FOUND("LEAVE_006", "휴가 정책을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
  LEAVE_POLICY_INACTIVE("LEAVE_007", "비활성화된 휴가 정책입니다.", HttpStatus.BAD_REQUEST),
  LEAVE_POLICY_DUPLICATE("LEAVE_008", "이미 존재하는 휴가 정책입니다.", HttpStatus.CONFLICT),
  LEAVE_POLICY_FORBIDDEN("LEAVE_009", "휴가 정책에 대한 권한이 없습니다.", HttpStatus.FORBIDDEN);

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



