package org.hit.hradar.domain.company.command.application.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.company.CompanyErrorCode;
import org.hit.hradar.domain.company.command.application.dto.request.UpdateCompanyRequest;
import org.hit.hradar.domain.company.command.application.dto.response.UpdateCompanyResponse;
import org.hit.hradar.domain.company.command.domain.aggregate.Company;
import org.hit.hradar.domain.company.command.domain.repository.CompanyRepository;
import org.hit.hradar.domain.companyApplication.command.domain.aggregate.CompanyApplication;
import org.hit.hradar.domain.companyApplication.command.domain.aggregate.CompanyApplicationStatus;
import org.hit.hradar.global.dto.AuthUser;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class CompanyCommandService {


  private final CompanyRepository companyRepository;

  // 회사 승인
  @Transactional
  public Company createFromApplication(CompanyApplication app, String companyCode) {
    // 신청서 정보를 기반으로 회사 엔티티를 생성하여 저장한다.
    return companyRepository.save(
        Company.builder()
            .appId(app.getAppId())                      // 신청서 PK 참조
            .comCode(companyCode)                       // 유니크 회사코드
            .companyName(app.getCompanyName())          // 회사명
            .bizNo(app.getBizNo())                      // 사업자번호
            .comTel(app.getComTel())                    // 연락처
            .address(app.getAddress())                  // 주소
            .status(CompanyApplicationStatus.APPROVED)  // 승인 상태로 생성
            .isDeleted('N')                             // 소프트 삭제 플래그 기본값
            .build()
    );
  }

  // 회사 정보 수정
  @Transactional
  public UpdateCompanyResponse updateCompany(Long comId, UpdateCompanyRequest request, AuthUser authUser) {

    Long requesterComId = authUser.companyId();

    // 회사 조회
    Company company = companyRepository.findById(comId)
        .orElseThrow(() -> new BusinessException(CompanyErrorCode.COMPANY_NOT_FOUND));

    // 삭제 여부 체크 (삭제된 회사면 수정 금지)
    if (company.getIsDeleted() != null && company.getIsDeleted() == 'Y') {
      throw new BusinessException(CompanyErrorCode.COMPANY_DELETED);
    }

    // 요청자의 회사ID와 수정 대상 회사ID가 동일해야 함

    if (requesterComId == null || !requesterComId.equals(comId)) {
      throw new BusinessException(CompanyErrorCode.FORBIDDEN);
    }


    company.updateInfo(
        request.getComName(),
        request.getComTel(),
        request.getAddress()
    );


    return UpdateCompanyResponse.builder()
        .companyId(company.getCompanyId())
        .name(company.getCompanyName())
        .build();
  }

  // 회사 삭제
  @Transactional
  public void deleteCompany(Long comId, AuthUser authUser) {

    Long requesterComId = authUser.companyId();

    // 회사 조회
    Company company = companyRepository.findById(comId)
        .orElseThrow(() -> new BusinessException(CompanyErrorCode.COMPANY_NOT_FOUND));

    // 이미 삭제된 경우는 멱등 처리
    if (company.getIsDeleted() != null && company.getIsDeleted() == 'Y') {
      return;
    }

    if (requesterComId == null || !requesterComId.equals(comId)) {
       throw new BusinessException(CompanyErrorCode.FORBIDDEN);
    }

    company.isDeleted();
  }
}
