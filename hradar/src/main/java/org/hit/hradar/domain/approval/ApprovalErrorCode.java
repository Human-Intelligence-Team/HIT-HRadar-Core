package org.hit.hradar.domain.approval;

import lombok.Getter;
import org.hit.hradar.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ApprovalErrorCode implements ErrorCode {
  CANNOT_SUBMIT_NOT_DRAFT("APP_001", "임시저장 상태의 문서만 제출할 수 있습니다." , HttpStatus.BAD_REQUEST),
  CANNOT_APPROVE_NON_PENDING_STEP("APP_002", "현재 결재 차례가 아닌 단계는 승인할 수 없습니다.", HttpStatus.BAD_REQUEST),
  CANNOT_REJECT_NON_PENDING_STEP("APP_003", "현재 결재 차례가 아닌 단계는 반려할 수 없습니다.", HttpStatus.BAD_REQUEST),
  NOT_ALLOWED_APPROVER("APP_004", "해당 결재 단계를 처리할 권한이 없습니다.", HttpStatus.FORBIDDEN),
  REJECT_REASON_REQUIRED("APP_005", "반려 사유는 필수 입력 항목입니다.", HttpStatus.BAD_REQUEST),
  CANNOT_WITHDRAW_AFTER_APPROVAL_STARTED("APP_006", "결재가 이미 시작된 문서는 회수할 수 없습니다.", HttpStatus.BAD_REQUEST),
  DOCUMENT_NOT_FOUND("APP_007", "결재 문서를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
  LINE_NOT_FOUND("APP_008", "결재선을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
  NO_PENDING_STEP("APP_009", "현재 처리할 결재 단계가 존재하지 않습니다.", HttpStatus.BAD_REQUEST);



  private final String errorCode;
  private final String message;
  private final HttpStatus httpStatus;

  ApprovalErrorCode(String errorCode, String message, HttpStatus httpStatus) {
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