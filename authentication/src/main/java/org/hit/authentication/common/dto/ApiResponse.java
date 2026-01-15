package org.hit.authentication.common.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ApiResponse<T> {

  private boolean success;
  private T data;
  private String errorCode;
  private String message;
  private LocalDateTime timestamp;

  public static <T> ApiResponse<T> success(T data) {
    return ApiResponse.<T>builder()
        .success(true)
        .data(data)
        .timestamp(LocalDateTime.now())
        .build();
  }

  public static <T> ApiResponse<T> failure(String errorCode, String message) {
    return ApiResponse.<T>builder()
        .success(false)
        .errorCode(errorCode)
        .message(message)
        .timestamp(LocalDateTime.now())
        .build();
  }
}