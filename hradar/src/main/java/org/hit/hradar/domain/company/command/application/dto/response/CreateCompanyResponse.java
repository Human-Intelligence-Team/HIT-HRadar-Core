package org.hit.hradar.domain.company.command.application.dto.response;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateCompanyResponse {
  private Long companyId;     // 회사 PK
  private String companyCode; // 회사코드(발급된 경우)
}
