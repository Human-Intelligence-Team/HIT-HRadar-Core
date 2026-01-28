package org.hit.hradar.domain.company.command.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hit.hradar.domain.company.command.domain.aggregate.Company;

@Getter
@AllArgsConstructor
public class CompanyProvisionResult {
  private final Long comId;         // 생성된 회사 PK
  private final String companyCode; // 발급된 회사코드
  private final Company company;    // 필요시 전체 엔티티
}