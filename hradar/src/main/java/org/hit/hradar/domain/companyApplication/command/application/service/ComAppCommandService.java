package org.hit.hradar.domain.companyApplication.command.application.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.company.command.domain.aggregate.Company;
import org.hit.hradar.domain.companyApplication.command.application.dto.CreateComAppRequest;
import org.hit.hradar.domain.companyApplication.command.application.dto.CreateComAppResponse;
import org.hit.hradar.domain.companyApplication.command.domain.aggregate.CompanyApplication;
import org.hit.hradar.domain.companyApplication.command.domain.repository.ComAppCommandRepository;
import org.hit.hradar.domain.companyApplication.command.domain.aggregate.CompanyApplicationStatus;
import org.hit.hradar.domain.user.UserErrorCode;
import org.hit.hradar.domain.user.command.domain.repository.UserRepository;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ComAppCommandService {

  private final ComAppCommandRepository companyApplicationCommandRepository;
  private final UserRepository userRepository;

  @Transactional
  public CreateComAppResponse create(CreateComAppRequest req) {

    //loginId 전역 유니크 선검사
    if (userRepository.existsByLoginId(req.getComAdminLoginId())) {
      throw new BusinessException(UserErrorCode.DUPLICATE_LOGIN_ID);
    }

    CompanyApplication app = CompanyApplication.builder()
        .comName(req.getComName())
        .ceoName(req.getCeoName())
        .bizNo(req.getBizNo())
        .comTel(req.getComTel())
        .address(req.getAddress())
        .status(CompanyApplicationStatus.SUBMITTED)
        .comAdminName(req.getComAdminName())
        .comAdminEmail(req.getComAdminEmail())
        .comAdminLoginId(req.getComAdminLoginId())
        .build();

    CompanyApplication saved = companyApplicationCommandRepository.save(app);

    return CreateComAppResponse.builder()
        .applicationId(saved.getApplicationId())
        .companyApplicationStatus(saved.getStatus().name())
        .build();
  }

  @Transactional
  public ApproveComAppResult approve(Long applicationId) {

    CompanyApplication app = companyApplicationCommandRepository.findById(applicationId)
        .orElseThrow(/* ... */);

    app.validatePending(); // PENDING 체크 같은거

    // 1) 회사 생성
    Company company = companyCreator.createFrom(app);

    // 2) 기본 Role 4개 생성
    DefaultRoles roles = defaultRoleSeeder.seed(company.getComId());

    // 3) 계정 생성(임시비번 생성 + 암호화 저장)
    CreatedAccount admin = accountCreator.createAdminAccount(app.getComAdminLoginId());

    // 4) 사원 생성(최고관리자 사원 레코드)
    Employee adminEmp = employeeCreator.createAdminEmployee(company.getComId(), admin.getAccountId(), app);

    // 5) 최고관리자 Role 부여
    roleAssigner.assign(adminEmp.getEmpId(), roles.getSuperAdminRoleId());

    // 6) 신청 상태 업데이트
    app.approveNow();

    return ApproveCompanyApplicationResult.builder()
        .companyId(company.getComId())
        .companyCode(company.getCompanyCode())
        .adminAccountId(admin.getAccountId())
        .adminLoginId(admin.getLoginId())
        .tempPassword(admin.getTempPassword()) // 서면 전달용 1회
        .build();
  }
}
