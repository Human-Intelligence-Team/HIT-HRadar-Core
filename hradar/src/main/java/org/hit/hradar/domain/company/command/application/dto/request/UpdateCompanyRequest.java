package org.hit.hradar.domain.company.command.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.*;

//회사 수정 dto
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateCompanyRequest {

  @NotBlank(message = "회사이름은 필수 입니다.")
  @Size(max = 100)
  private String comName;

  @Size(max = 255)
  private String address;

  @Size(max = 30)
  private String comTel;

  @Size(max = 10)
  private LocalDate foundDate;

  private String comEmail;
  private String ceoName;
}