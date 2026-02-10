package org.hit.hradar.domain.company.query.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.company.CompanyErrorCode;
import org.hit.hradar.domain.company.query.dto.CompanyDetailResponse;
import org.hit.hradar.domain.company.query.dto.CompanyResponse;
import org.hit.hradar.domain.company.query.dto.CompanySearchCondition;
import org.hit.hradar.domain.company.query.dto.CompanySearchRequest;
import org.hit.hradar.domain.company.query.mapper.CompanyQueryMapper;
import org.hit.hradar.global.exception.BusinessException;
import org.hit.hradar.global.query.paging.PageResponse;
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
  public PageResponse<CompanyResponse> getCompanies(CompanySearchRequest req) {
    CompanySearchCondition cond = req.getCond();
    int page = req.getPage().safePage();
    int size = req.getPage().safeSize();
    int offset = req.getPage().offset();

    List<CompanyResponse> items = companyQueryMapper.findAll(cond, offset, size);
    long total = companyQueryMapper.count(cond);

    return PageResponse.of(items, page, size, total);
  }

  @Transactional(readOnly = true)
  public CompanyDetailResponse getCompany(Long comId, Long companyId, String role) {

    // 플랫폼 최고 관리자(admin)가 아니면서, 다른 회사의 정보를 조회하려는 경우 차단
    if (!"admin".equalsIgnoreCase(role)) {
      if (companyId == null || !companyId.equals(comId)) {
        throw new BusinessException(CompanyErrorCode.FORBIDDEN);
      }
    }

    CompanyDetailResponse company = companyQueryMapper.findById(comId);
    if (company == null) {
      throw new BusinessException(CompanyErrorCode.COMPANY_NOT_FOUND);
    }

    return company;
  }
}
