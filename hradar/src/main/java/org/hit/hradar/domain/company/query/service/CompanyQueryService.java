package org.hit.hradar.domain.company.query.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.company.CompanyErrorCode;
import org.hit.hradar.domain.company.query.dto.CompanyDetailResponse;
import org.hit.hradar.domain.company.query.dto.CompanyResponse;
import org.hit.hradar.domain.company.query.dto.CompanySearchCondition;
import org.hit.hradar.domain.company.query.mapper.CompanyQueryMapper;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CompanyQueryService {

  private final CompanyQueryMapper companyQueryMapper;

  @Transactional(readOnly = true)
  public CompanyDetailResponse getMyCompany(Long companyId) {

    // 로그인 주체가 소속 회사가 없는 경우 예외처리
    if (companyId == null) {
      throw new BusinessException(CompanyErrorCode.FORBIDDEN);
    }

    CompanyDetailResponse company = companyQueryMapper.findById(companyId);
    if (company == null) {
      throw new BusinessException(CompanyErrorCode.COMPANY_NOT_FOUND);
    }

    return company;
  }

  @Transactional(readOnly = true)
  public List<CompanyResponse> getCompanies(CompanySearchCondition condition) {
    return companyQueryMapper.findAll(condition.getKeyword(), condition.getIncludeDeleted());
  }

  @Transactional(readOnly = true)
  public CompanyDetailResponse getCompany(Long companyId) {

    CompanyDetailResponse company = companyQueryMapper.findById(companyId);
    if (company == null) {
      throw new BusinessException(CompanyErrorCode.COMPANY_NOT_FOUND);
    }

    return company;
  }
}
