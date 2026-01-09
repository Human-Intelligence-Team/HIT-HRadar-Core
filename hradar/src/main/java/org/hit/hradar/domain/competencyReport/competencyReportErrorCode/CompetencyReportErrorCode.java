package org.hit.hradar.domain.competencyReport.competencyReportErrorCode;

import org.hit.hradar.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public enum CompetencyReportErrorCode implements ErrorCode {

  // report 관련 ErrorCode

  // Report Contents 관련 Error Code

  // Contents 관련 ErrorCode
  CONTENT_TITLE_REQUIRED(
      "CR-001",
      "컨텐츠 제목은 필수 입력 값입니다.",
      HttpStatus.BAD_REQUEST
  ),

  CONTENT_TYPE_REQUIRED(
      "CR-002",
      "컨텐츠 타입은 필수 입력 값입니다.",
      HttpStatus.BAD_REQUEST
  ),

  CONTENT_LEVEL_REQUIRED(
      "CR-003",
      "컨텐츠 난이도는 필수 입력 값입니다.",
      HttpStatus.BAD_REQUEST
  ),
  // Contents Tag 관련 ErrorCode

  // Tag 관련 ErrorCode

  ;
  private final String errorCode;
  private final String message;
  private final HttpStatus httpStatus;

  CompetencyReportErrorCode(String errorCode, String message, HttpStatus httpStatus) {
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
