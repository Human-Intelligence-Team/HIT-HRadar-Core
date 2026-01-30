package org.hit.hradar.domain.companyApplication.command.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateComAppRequest {

  @NotBlank @Size(max = 200)
  private String companyName;

  @NotBlank @Size(max = 30)
  private String bizNo;

  @NotBlank @Size(max = 30)
  private String comTel;

  @NotBlank @Size(max = 255)
  private String address;

  @NotBlank @Size(max = 50)
  private String name;

  @NotBlank @Size(max = 100)
  private String email;

  @NotBlank @Size(max = 100)
  private String loginId; //사용자가 ID입력
}
