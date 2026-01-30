package org.hit.hradar.domain.companyApplication.command.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RejectComAppRequest {

  @NotBlank(message = "반려 사유는 필수입니다.")
  @Size(max = 500, message = "반려 사유는 최대 500자까지 가능합니다.")
  private String reason;

}
