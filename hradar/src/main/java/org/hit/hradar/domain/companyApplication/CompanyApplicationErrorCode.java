package org.hit.hradar.domain.companyApplication;

import org.hit.hradar.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum CompanyApplicationErrorCode implements ErrorCode {

  APPLICATION_NOT_FOUND( "ComApp_001" , "해당 신청이 존재하지 않습니다.", HttpStatus.NOT_FOUND),
  INVALID_STATUS("ComApp_002", "해당 상태값이 존재하지 않습니다.", HttpStatus.NOT_FOUND),
  INVALID_REVIEWER("ComApp_003", "리뷰어가 존재하지 않습니다.",HttpStatus.NOT_FOUND),
  INVALID_REJECT_REASON("ComApp_004", "거절사유를 작성하십시오.",HttpStatus.BAD_REQUEST),
  ;



 private final String errorCode;
 private final String message;
 private final HttpStatus httpStatus;

 CompanyApplicationErrorCode(String errorCode, String message, HttpStatus httpStatus) {
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
