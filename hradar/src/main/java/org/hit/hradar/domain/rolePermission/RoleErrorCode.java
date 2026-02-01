package org.hit.hradar.domain.rolePermission;

import org.hit.hradar.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public enum RoleErrorCode implements ErrorCode {
  ROLE_NOT_FOUND("ROLE_001", "해당 역할을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
  SYSTEM_ROLE_CANNOT_UPDATE("ROLE_002", "기본 역할은 수정할 수 없습니다.", HttpStatus.FORBIDDEN),
  SYSTEM_ROLE_CANNOT_DELETE("ROLE_003", "기본 역할은 삭제할 수 없습니다.", HttpStatus.FORBIDDEN),
  SYSTEM_ROLE_ONLY_OPERATION("ROLE_004", "기본 역할 권한 조정은 플랫폼 관리자만 가능합니다.", HttpStatus.FORBIDDEN),
  INVALID_PERMISSION_ID("ROLE_005", "유효하지 않은 권한 ID가 포함되어 있습니다.", HttpStatus.BAD_REQUEST),
  DUPLICATE_PERMISSION_KEY("ROLE_006", "이미 존재하는 권한 키입니다.", HttpStatus.CONFLICT),
  PERMISSION_NOT_FOUND("ROLE_007", "해당 권한을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
  PERMISSION_DENIED("ROLE_008", "권한이 없습니다.", HttpStatus.FORBIDDEN),
  ;

  private final String errorCode;
  private final String message;
  private final HttpStatus httpStatus;

  RoleErrorCode(String errorCode, String message, HttpStatus httpStatus) {
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