package org.hit.hradar.domain.company.command.application.dto.response;

import lombok.*;

// 회사 수정 dto
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateCompanyResponse {

  private Long companyId; // 회사 PK
  private String name;    // 회사명
}
