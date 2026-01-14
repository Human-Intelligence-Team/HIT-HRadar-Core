package org.hit.hradar.domain.company.command.application.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.company.command.domain.aggregate.Company;
import org.hit.hradar.domain.company.command.domain.repository.CompanyRepository;
import org.hit.hradar.domain.companyApplication.command.domain.aggregate.CompanyApplication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CompanyCommandService {

  private final CompanyRepository companyRepository;

  /**
   * 회사 신청 승인 시: 회사 생성 (회사코드는 외부에서 확정된 값만 받음)
   */
  @Transactional
  public Company create(CompanyApplication app, String companyCode) {

    Company company = Company.builder()
        .comCode(companyCode)
        .name(app.getComName())
        .ceoName(app.getCeoName())
        .bizNo(app.getBizNo())
        .comTel(app.getComTel())
        .address(app.getAddress())
        .build();

    return companyRepository.save(company);
  }
}
