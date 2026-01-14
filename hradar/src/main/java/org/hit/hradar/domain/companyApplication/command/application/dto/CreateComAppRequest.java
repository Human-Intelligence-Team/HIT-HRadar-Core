package org.hit.hradar.domain.companyApplication.command.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateComAppRequest {

  @NotBlank @Size(max = 200)
  private String comName;

  @NotBlank @Size(max = 100)
  private String ceoName;

  @NotBlank @Size(max = 30)
  private String bizNo;

  @NotBlank @Size(max = 30)
  private String comTel;

  @NotBlank @Size(max = 255)
  private String address;

  @NotBlank @Size(max = 50)
  private String comAdminName;

  @NotBlank @Email @Size(max = 100)
  private String comAdminEmail;

  @NotBlank @Size(max = 100)
  private String comAdminLoginId; //사용자가 ID입력 (회사 신청시에는 중복검사X)
}
