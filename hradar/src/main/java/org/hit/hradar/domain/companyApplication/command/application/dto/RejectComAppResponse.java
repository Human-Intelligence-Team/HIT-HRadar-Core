package org.hit.hradar.domain.companyApplication.command.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RejectComAppResponse {

  @NotBlank @Size(max = 500)
  private String rejectReason;

  @NotBlank
  private LocalDateTime reviewedAt;
}
