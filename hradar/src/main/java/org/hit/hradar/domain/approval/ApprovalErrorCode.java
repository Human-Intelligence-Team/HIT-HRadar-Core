package org.hit.hradar.domain.approval;

import lombok.Getter;
import org.hit.hradar.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ApprovalErrorCode implements ErrorCode {
  DOCUMENT_NOT_FOUND("APP_007", "결재 문서를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
  LINE_NOT_FOUND("APP_008", "결재선을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
  NOT_ALLOWED_SUBMIT("APP_010", "해당 결재를 제출할 권한이 없습니다.", HttpStatus.BAD_REQUEST),
  NOT_ALLOWED_APPROVER("APP_004", "해당 결재 단계를 처리할 권한이 없습니다.", HttpStatus.FORBIDDEN),
  CANNOT_SUBMIT_NOT_DRAFT("APP_001", "임시저장 상태의 문서만 제출할 수 있습니다." , HttpStatus.BAD_REQUEST),
  NO_PENDING_STEP("APP_009", "현재 처리할 결재 단계가 존재하지 않습니다.", HttpStatus.BAD_REQUEST),
  CANNOT_APPROVE_NON_PENDING_STEP("APP_002", "현재 결재 차례가 아닌 단계는 승인할 수 없습니다.", HttpStatus.BAD_REQUEST),
  CANNOT_REJECT_NON_PENDING_STEP("APP_003", "현재 결재 차례가 아닌 단계는 반려할 수 없습니다.", HttpStatus.BAD_REQUEST),
  REJECT_REASON_REQUIRED("APP_005", "반려 사유는 필수 입력 항목입니다.", HttpStatus.BAD_REQUEST),
  CANNOT_WITHDRAW_AFTER_APPROVAL_STARTED("APP_006", "결재가 이미 시작된 문서는 회수할 수 없습니다.", HttpStatus.BAD_REQUEST),
  CANNOT_EDIT_AFTER_SUBMIT("APP_011", "상신 이후에는 문서를 수정할 수 없습니다.", HttpStatus.BAD_REQUEST),
  NOT_ALLOWED_EDIT("APP_012", "해당 결재 문서를 수정할 권한이 없습니다.", HttpStatus.FORBIDDEN),
  ALREADY_SUBMITTED("APP_013", "이미 상신된 결재 문서입니다.", HttpStatus.BAD_REQUEST),
  APPROVER_REQUIRED("APP_014", "결재자는 최소 1명 이상 지정되어야 합니다.", HttpStatus.BAD_REQUEST),
  INVALID_DOCUMENT_TYPE("APP_015", "지원하지 않는 결재 문서 유형입니다.", HttpStatus.BAD_REQUEST),
  DOMAIN_HANDLER_NOT_FOUND("APP_016", "결재 문서 유형에 대한 처리기를 찾을 수 없습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
  DOMAIN_PAYLOAD_REQUIRED("APP_017", "결재 문서 유형에 필요한 추가 정보가 누락되었습니다.", HttpStatus.BAD_REQUEST),
  DOMAIN_PAYLOAD_INVALID("APP_018", "결재 문서 유형에 대한 입력 정보가 올바르지 않습니다.", HttpStatus.BAD_REQUEST),
  INVALID_SAVE_MODE("APP_020","지원하지 않는 결재 저장 모드입니다.",HttpStatus.BAD_REQUEST),
  INVALID_DOC_TYPE_FORMAT("APP_021","결재 문서 유형은 필수입니다.",HttpStatus.BAD_REQUEST),
  INVALID_REQUEST("APP_022", "요청 값이 올바르지 않습니다.", HttpStatus.BAD_REQUEST),
  NOT_ALLOWED_COMMENT("APP_023", "결재 문서에 댓글을 작성할 수 없습니다.", HttpStatus.FORBIDDEN);



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