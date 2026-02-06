package org.hit.hradar.domain.competencyReport.command.application.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CustomCodeCreateRequest {

  @NotNull
  private Long customCodeId;

  @NotBlank(message = "코드는 필수입니다.")
  @Size(max = 45, message = "코드는 최대 45자까지 입력할 수 있습니다.")
  private String customCode;

  @NotBlank(message = "코드명은 필수입니다.")
  @Size(max = 45, message = "코드명은 최대 45자까지 입력할 수 있습니다.")
  private String customName;

  @Size(max = 100, message = "코드명은 최대 100자까지 입력할 수 있습니다.")
  private String customDesc;

  private Character isDeleted;

  public CustomCodeCreateRequest(Long customCodeId, String customCode, String customName, String customDesc, Character isDeleted) {
    this.customCodeId = customCodeId;
    this.customCode = customCode;
    this.customName = customName;
    this.customDesc = customDesc;
    this.isDeleted = isDeleted;
  }
}
