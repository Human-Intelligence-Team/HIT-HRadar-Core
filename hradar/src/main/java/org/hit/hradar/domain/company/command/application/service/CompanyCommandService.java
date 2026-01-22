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

  // 회사 신청 승인 플로우용 (CompanyApplication -> Company)
// CompanyApplication(appId)을 참조하여 Company를 생성/저장

  @Transactional
  public Company createFromApplication(CompanyApplication app, String companyCode) {
    return companyRepository.save(
        Company.builder()
            .appId(app.getAppId())
            .comCode(companyCode)
            .companyName(app.getCompanyName())
            .bizNo(app.getBizNo())
            .comTel(app.getComTel())
            .address(app.getAddress())
            .status(CompanyApplicationStatus.APPROVED)
            .isDeleted('N')
            .build()
    );
  }

  /**
   * 회사 수정 플로우 - comId로 회사 조회 후 변경 가능한 필드만 업데이트한다. - 소프트 삭제된 회사는 수정 불가로 막는다(정책).
   */
  @Transactional
  public UpdateCompanyResponse updateCompany(Long comId, UpdateCompanyRequest request, Long requesterAccId, Long requesterComId) {

    // 1) 회사 조회 (없으면 예외)
    Company company = companyRepository.findById(comId)
        .orElseThrow(() -> new BusinessException(CompanyErrorCode.COMPANY_NOT_FOUND));

    // 2) 소프트 삭제 여부 체크 (삭제된 회사면 수정 금지)
    if (company.getIsDeleted() != null && company.getIsDeleted() == 'Y') {
      throw new BusinessException(CompanyErrorCode.COMPANY_DELETED);
    }

    if (requesterComId == null || !requesterComId.equals(comId)) {
      throw new BusinessException(CompanyErrorCode.FORBIDDEN);
    }

    company.updateInfo(
        request.getComName(),  // 회사명 변경
        request.getComTel(),   // 연락처 변경
        request.getAddress()   // 주소 변경
    );

    return UpdateCompanyResponse.builder()
        .companyId(company.getCompanyId()) // 엔티티 PK getter명에 맞춰 수정(getComId면 그걸로)
        .name(company.getCompanyName())
        .build();
  }

  /**
   * 회사 삭제 플로우(소프트 삭제) - 실제 delete가 아니라 isDeleted='Y' 처리로 운영한다.
   */
  @Transactional
  public void deleteCompany(Long comId, Long requesterAccId) {

    // 1) 회사 조회
    Company company = companyRepository.findById(comId)
        .orElseThrow(() -> new BusinessException(CompanyErrorCode.COMPANY_NOT_FOUND));

    // 2) 이미 삭제된 경우는 멱등 처리
    if (company.getIsDeleted() != null && company.getIsDeleted() == 'Y') {
      return;
    }

    // 3) 소프트 삭제 처리
    company.isDeleted();
  }

  }

