package org.hit.hradar.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.hit.hradar.global.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
/*
import org.springframework.security.authorization.AuthorizationDeniedException;
*/
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<ApiResponse<?>> handleBusinessException(BusinessException ex) {
    log.error("[BUSINESS ERROR] code={} message={}", ex.getErrorCode(), ex.getMessage());
    return ResponseEntity.status(ex.getErrorCode().getHttpStatusCode())
                         .body(ApiResponse.failure(ex.getErrorCode().getErrorCode(), ex.getMessage()));
  }

/*  @ExceptionHandler(AuthorizationDeniedException.class)
  public ResponseEntity<ApiResponse<?>> handleAuthorizationDenied(AuthorizationDeniedException ex) {
    log.error("[ACCESS DENIED ERROR] message={}", ex.getMessage());
    return ResponseEntity.status(HttpStatus.FORBIDDEN)  // 403 Forbidden
                         .body(ApiResponse.failure("ACCESS_DENIED", "접근 권한이 없습니다."));
  }*/

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse<?>> handleException(Exception ex) {
    log.error("[SYSTEM ERROR]", ex);
    return ResponseEntity.internalServerError()
                         .body(ApiResponse.failure("SYSTEM_ERROR", "서버 오류입니다."));
  }
}
