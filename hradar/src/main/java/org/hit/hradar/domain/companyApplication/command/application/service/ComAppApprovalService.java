package org.hit.hradar.domain.companyApplication.command.application.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.company.command.application.dto.response.CompanyProvisionResult;
import org.hit.hradar.domain.company.command.application.service.CompanyProvisionService;
import org.hit.hradar.domain.companyApplication.CompanyApplicationErrorCode;
import org.hit.hradar.domain.companyApplication.command.application.dto.ApproveComAppResponse;
import org.hit.hradar.domain.companyApplication.command.application.dto.RejectComAppResponse;
import org.hit.hradar.domain.companyApplication.command.domain.aggregate.CompanyApplication;
import org.hit.hradar.domain.companyApplication.command.domain.aggregate.CompanyApplicationStatus;
import org.hit.hradar.domain.companyApplication.command.domain.repository.ComAppRepository;
import org.hit.hradar.domain.employee.command.application.dto.request.CreateFirstEmpRequest;
import org.hit.hradar.domain.employee.command.application.dto.reponse.CreateFirstEmpResponse;
import org.hit.hradar.domain.employee.command.application.service.EmployeeCommandService;
import org.hit.hradar.domain.rolePermission.command.application.service.DefaultRoleCommandService;
import org.hit.hradar.domain.rolePermission.command.application.service.EmployeeRoleAssignmentApplicationService;
import org.hit.hradar.domain.user.UserErrorCode;
import org.hit.hradar.domain.user.command.application.dto.request.CreateFirstUserRequest;
import org.hit.hradar.domain.user.command.application.service.UserService;
import org.hit.hradar.global.dto.AuthUser;
import org.hit.hradar.global.exception.BusinessException;
import org.hit.hradar.global.util.RandomGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ComAppApprovalService {

  private final ComAppRepository comAppRepository;
  private final CompanyProvisionService companyProvisionService;
  private final DefaultRoleCommandService defaultRoleCommandService;
  private final EmployeeCommandService employeeCommandService;
  private final EmployeeRoleAssignmentApplicationService employeeRoleAssignmentApplicationService;
  private final UserService userAccountService;

  // 신청서 승인(userrole = admin)
  @Transactional
  public ApproveComAppResponse approve(Long applicationId, AuthUser authUser) {

    // 플랫폼 관리자만 승인 가능: role만 보고 판단 (DB 조회 금지)
    if (authUser == null || authUser.role() == null || !"admin".equalsIgnoreCase(authUser.role())) {
      throw new BusinessException(UserErrorCode.FORBIDDEN);
    }

    // 신청서 락 조회
    CompanyApplication app = comAppRepository.findByIdForUpdate(applicationId)
        .orElseThrow(() -> new BusinessException(CompanyApplicationErrorCode.APPLICATION_NOT_FOUND));

    // 상태 가드
    if (app.getStatus() != CompanyApplicationStatus.SUBMITTED) {
      throw new BusinessException(CompanyApplicationErrorCode.INVALID_STATUS);
    }

    // 승인 처리(승인자 id 기록은 '그대로' 저장만 하고 조회는 안 함)
    app.approveNow(authUser.userId());

    // 회사 생성 + 코드 발급
    CompanyProvisionResult result = companyProvisionService.provisionFromApplication(app);
    Long comId = result.getComId();
    String companyCode = result.getCompanyCode();

    // 기본 역할 생성
    defaultRoleCommandService.ensureDefaults(comId);

    // 첫 사원 생성
    CreateFirstEmpRequest firstEmpReq = new CreateFirstEmpRequest(comId, app.getName(), app.getEmail());
    CreateFirstEmpResponse firstEmpRes = employeeCommandService.createFirstEmployee(firstEmpReq);
    Long empId = firstEmpRes.getEmpId();

    // 첫 사원 역할 부여
    employeeRoleAssignmentApplicationService.assignForFirstEmployee(comId, empId);

    // 임시 비번
    String tempPassword = RandomGenerator.generateTempPassword(10);

    // 첫 계정 생성
    CreateFirstUserRequest admin = userAccountService.createFirstUserRequest(
        comId, companyCode, empId, app.getLoginId(), app.getName(), app.getEmail(), tempPassword
    );

    return ApproveComAppResponse.builder()
        .appId(app.getAppId())
        .status(app.getStatus().name())
        .comId(comId)
        .companyCode(companyCode)
        .loginId(admin.getLoginId())
        .password(admin.getPassword())
        .build();
  }

  // 신청서 거절(userrole = admin)
  @Transactional
  public RejectComAppResponse reject(Long applicationId, AuthUser authUser, String reason) {

    if (authUser == null || authUser.role() == null || !"admin".equalsIgnoreCase(authUser.role())) {
      throw new BusinessException(UserErrorCode.FORBIDDEN);
    }

    CompanyApplication app = comAppRepository.findByIdForUpdate(applicationId)
        .orElseThrow(() -> new BusinessException(CompanyApplicationErrorCode.APPLICATION_NOT_FOUND));

    // reviewer는 authUser.userId()로 기록
    app.rejectNow(reason, authUser.userId());

    return RejectComAppResponse.builder()
        .appId(app.getAppId())
        .status(app.getStatus())
        .rejectReason(app.getRejectReason())
        .reviewedAt(app.getReviewedAt())
        .build();
  }
}
