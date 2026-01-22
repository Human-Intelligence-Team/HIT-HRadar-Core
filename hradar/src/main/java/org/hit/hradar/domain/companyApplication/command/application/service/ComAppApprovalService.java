package org.hit.hradar.domain.companyApplication.command.application.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.company.command.application.dto.response.CompanyProvisionResult;
import org.hit.hradar.domain.company.command.application.service.CompanyProvisionService;
import org.hit.hradar.domain.companyApplication.CompanyApplicationErrorCode;
import org.hit.hradar.domain.companyApplication.command.application.dto.ApproveComAppResponse;
import org.hit.hradar.domain.companyApplication.command.domain.aggregate.CompanyApplication;
import org.hit.hradar.domain.companyApplication.command.domain.aggregate.CompanyApplicationStatus;
import org.hit.hradar.domain.companyApplication.command.infrastructure.ComAppJpaRepository;
import org.hit.hradar.domain.employee.command.application.dto.CreateFirstEmpRequest;
import org.hit.hradar.domain.employee.command.application.dto.CreateFirstEmpResponse;
import org.hit.hradar.domain.employee.command.application.service.EmployeeCommandService;
import org.hit.hradar.domain.rolePermission.command.application.service.DefaultRoleCommandService;
import org.hit.hradar.domain.rolePermission.command.application.service.EmployeeRoleAssignmentApplicationService;
import org.hit.hradar.domain.user.command.application.dto.CreateFirstUserResponse;
import org.hit.hradar.domain.user.command.application.service.UserService;
import org.hit.hradar.global.exception.BusinessException;
import org.hit.hradar.global.util.RandomGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ComAppApprovalService {

  private final UserService userService; // 승인자 권한(플랫폼 관리자) 체크용
  private final ComAppJpaRepository comAppJpaRepository; // 신청서 락 조회용

  private final CompanyProvisionService companyProvisionService; // 회사코드 발급 + 회사 생성
  private final DefaultRoleCommandService defaultRoleCommandService; // 기본 역할 생성
  private final EmployeeCommandService employeeCommandService; // 첫 사원 생성
  private final EmployeeRoleAssignmentApplicationService employeeRoleAssignmentApplicationService; // 첫 사원 역할 자동 부여
  private final UserService userAccountService; // 관리자 계정 생성(프로젝트에선 UserService가 담당)

  @Transactional
  public ApproveComAppResponse approve(Long applicationId, Long approverUserId) {

    // 1) 승인자 권한 체크: 플랫폼 관리자만 승인 가능
    userService.requirePlatformAdmin(approverUserId);

    // 2) 신청서 비관적 락으로 조회: 승인 동시성 방지
    CompanyApplication app = comAppJpaRepository.findByIdForUpdate(applicationId)
        .orElseThrow(() -> new BusinessException(CompanyApplicationErrorCode.APPLICATION_NOT_FOUND));

    // 3) 상태 가드: SUBMITTED만 승인 가능
    if (app.getStatus() != CompanyApplicationStatus.SUBMITTED) {
      throw new BusinessException(CompanyApplicationErrorCode.INVALID_STATUS);
    }

    // 4) 신청서 승인 처리(도메인 메서드): 상태/시간 세팅
    app.approveNow(approverUserId);

    // 5) 회사코드 발급 + 회사 생성
    CompanyProvisionResult result = companyProvisionService.provisionFromApplication(app);
    Long comId = result.getComId();
    String companyCode = result.getCompanyCode();

    // 6) 기본 역할 4개 보장 (OWNER/HRTEAM/TEAMLEADER/EMPLOYEE)
    defaultRoleCommandService.ensureDefaults(comId);

    // 7) 첫 사원 생성: 신청자 이름/이메일 기반
    CreateFirstEmpRequest firstEmpReq = new CreateFirstEmpRequest(
        comId,
        app.getName(),
        app.getEmail()
    );
    CreateFirstEmpResponse firstEmpRes = employeeCommandService.createFirstEmployee(firstEmpReq);
    Long empId = firstEmpRes.getEmpId();

    // 8) 첫 사원 역할 자동 부여: 정책 기반으로 OWNER + EMPLOYEE 등 부여
    employeeRoleAssignmentApplicationService.assignForFirstEmployee(comId, empId);

    // 9) 임시 비밀번호 생성: 초기 전달용(원문 그대로)
    String tempPassword = RandomGenerator.generateTempPassword(10);

    // 10) 관리자 계정 생성: 신청서의 loginId를 사용
    CreateFirstUserResponse admin = userAccountService.createFirstUserResponse(
        comId,
        companyCode,
        empId,
        app.getLoginId(),
        app.getName(),
        app.getEmail(),
        tempPassword
    );

    // 11) 프론트 응답: companyCode 포함
    return ApproveComAppResponse.builder()
        .appId(app.getAppId())
        .status(app.getStatus().name())
        .comId(comId)
        .companyCode(companyCode)
        .loginId(admin.getLoginId())
        .password(admin.getPassword())
        .build();
  }
}
