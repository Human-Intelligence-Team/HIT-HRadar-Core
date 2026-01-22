package org.hit.hradar.domain.company.command.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateCompanyRequest {

  @NotBlank
  private String companyCode;

  @NotBlank
  private String comName;

  @NotBlank
  private String bizNo;

  private String address;

  private String comTel;

  private String foundDate;


}
