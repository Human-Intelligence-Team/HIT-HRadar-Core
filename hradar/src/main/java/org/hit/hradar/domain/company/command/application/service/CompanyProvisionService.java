package org.hit.hradar.domain.company.command.application.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.company.command.application.dto.response.CompanyProvisionResult;
import org.hit.hradar.domain.company.command.domain.aggregate.Company;
import org.hit.hradar.domain.companyApplication.command.domain.aggregate.CompanyApplication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CompanyProvisionService {               //회사 코드생성 + 회사 생성 로직 합친 서비스

  private final CompanyCodeService companyCodeService;
  private final CompanyCommandService companyCommandService;

  @Transactional
  public CompanyProvisionResult provisionFromApplication(CompanyApplication app) {
    // 1) 회사코드 발급
    String companyCode = companyCodeService.issueUniqueCompanyCode();

    // 2) 회사 생성
    Company company = companyCommandService.createFromApplication(app, companyCode);

    // 3) comId 추출 후 결과로 묶어서 반환
    Long comId = company.getCompanyId();

    return new CompanyProvisionResult(comId, companyCode, company);
  }
}