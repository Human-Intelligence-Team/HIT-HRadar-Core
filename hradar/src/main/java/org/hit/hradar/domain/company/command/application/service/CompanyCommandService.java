package org.hit.hradar.domain.company.command.application.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.company.CompanyErrorCode;
import org.hit.hradar.domain.company.command.application.dto.request.UpdateCompanyRequest;
import org.hit.hradar.domain.company.command.application.dto.response.UpdateCompanyResponse;
import org.hit.hradar.domain.company.command.domain.aggregate.Company;
import org.hit.hradar.domain.company.command.domain.repository.CompanyRepository;
import org.hit.hradar.domain.companyApplication.command.domain.aggregate.CompanyApplication;
import org.hit.hradar.domain.companyApplication.command.domain.aggregate.CompanyApplicationStatus;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CompanyCommandService {

  private final CompanyRepository companyRepository;
  private final org.hit.hradar.domain.user.command.domain.repository.AccountRepository accountRepository;
  private final org.hit.hradar.domain.employee.command.domain.repository.EmployeeRepository employeeRepository;
  private final org.hit.hradar.domain.rolePermission.command.application.service.DefaultRoleCommandService defaultRoleCommandService;

  // 회사 승인
  @Transactional
  public Company createFromApplication(CompanyApplication app, String companyCode) {
    Company company = companyRepository.save(
        Company.builder()
            .appId(app.getAppId())
            .comCode(companyCode)
            .companyName(app.getCompanyName())
            .bizNo(app.getBizNo())
            .comTel(app.getComTel())
            .address(app.getAddress())
            .status(CompanyApplicationStatus.APPROVED)
            .isDeleted('N')
            .build());

    // 기본 역할 생성
    defaultRoleCommandService.ensureDefaults(company.getCompanyId());

    return company;
  }

  // 회사 정보 수정
  @Transactional
  public UpdateCompanyResponse updateCompany(Long comId, Long companyId, UpdateCompanyRequest request) {

    // 요청자의 회사ID와 수정 대상 회사ID가 동일해야 함
    if (companyId == null || !companyId.equals(comId)) {
      throw new BusinessException(CompanyErrorCode.FORBIDDEN);
    }

    Company company = companyRepository.findById(comId)
        .orElseThrow(() -> new BusinessException(CompanyErrorCode.COMPANY_NOT_FOUND));

    if (company.getIsDeleted() != null && company.getIsDeleted() == 'Y') {
      throw new BusinessException(CompanyErrorCode.COMPANY_DELETED);
    }

    company.updateInfo(
        request.getComName(),
        request.getComTel(),
        request.getAddress(),
        request.getComEmail(),
        request.getCeoName(),
        request.getFoundDate());

    return UpdateCompanyResponse.builder()
        .companyId(company.getCompanyId())
        .name(company.getCompanyName())
        .build();
  }

  // 회사 삭제
  @Transactional
  public void deleteCompany(Long comId, Long companyId, String role) {

    // 플랫폼 최고 관리자(admin)가 아니면서, 다른 회사를 삭제하려는 경우 차단
    if (!"admin".equalsIgnoreCase(role)) {
      if (companyId == null || !companyId.equals(comId)) {
        throw new BusinessException(CompanyErrorCode.FORBIDDEN);
      }
    }

    Company company = companyRepository.findById(comId)
        .orElseThrow(() -> new BusinessException(CompanyErrorCode.COMPANY_NOT_FOUND));

    // 이미 삭제된 경우는 멱등 처리
    if (company.getIsDeleted() != null && company.getIsDeleted() == 'Y') {
      return;
    }

    company.isDeleted();

    // 관련 계정 및 직원 비활성화
    accountRepository.deactivateAccountsByComId(comId);
    employeeRepository.deactivateEmployeesByComId(comId);
  }
}
