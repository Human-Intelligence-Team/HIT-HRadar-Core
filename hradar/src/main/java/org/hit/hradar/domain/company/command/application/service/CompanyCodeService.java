package org.hit.hradar.domain.company.command.application.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.company.CompanyErrorCode;
import org.hit.hradar.domain.company.command.domain.repository.CompanyRepository;
import org.hit.hradar.global.exception.BusinessException;
import org.hit.hradar.global.util.RandomGenerator;
import org.springframework.stereotype.Service;

  @Service
  @RequiredArgsConstructor
  public class CompanyCodeService {

    private static final int CODE_LEN = 6;
    private static final int MAX_RETRY = 30;

    private final CompanyRepository companyRepository;

    /**
     * 회사코드 "발급" 전용:
     * - 랜덤 생성
     * - DB 중복 여부 확인
     * - MAX_RETRY 동안 유니크 코드 못 만들면 예외
     */
    public String issueUniqueCompanyCode() {
      for (int i = 0; i < MAX_RETRY; i++) {
        String companyCode = RandomGenerator.generateCompanyCode(CODE_LEN);
        if (!companyRepository.existsByComCode(companyCode)) {
          return companyCode;
        }
      }
      throw new BusinessException(CompanyErrorCode.COMPANY_CODE_DUPLICATE);
    }
  }



