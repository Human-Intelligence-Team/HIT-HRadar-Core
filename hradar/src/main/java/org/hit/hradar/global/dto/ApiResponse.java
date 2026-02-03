package org.hit.hradar.global.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "공통 API 응답 포맷")
@Builder
@Getter
public class ApiResponse<T> {

  @Schema(description = "요청 성공 여부", example = "true")
  private boolean success;

  @Schema(description = "응답 데이터 (성공 시에만 존재)")
  private T data;

  @Schema(description = "에러 코드 (실패 시)", example = "TEMPLATE_001")
  private String errorCode;

  @Schema(description = "에러 메시지 (실패 시)", example = "해당 템플릿을 찾을 수 없습니다.")
  private String message;

  @Schema(description = "응답 생성 시각")
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