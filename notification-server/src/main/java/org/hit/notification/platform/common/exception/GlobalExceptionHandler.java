package org.hit.notification.platform.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.hit.notification.platform.common.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
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

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse<?>> handleException(Exception ex) {
    log.error("[SYSTEM ERROR]", ex);
    return ResponseEntity.internalServerError()
                         .body(ApiResponse.failure("SYSTEM_ERROR", "서버 오류입니다."));
  }
}
