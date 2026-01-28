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

  /**
   * 회사 신청 승인 (플랫폼 관리자만 가능)
   */
  @Transactional
  public ApproveComAppResponse approve(Long applicationId, Long reviewerUserId, String role) {

    // 플랫폼 관리자 권한 체크
    validateAdmin(reviewerUserId, role);

    CompanyApplication app = comAppRepository.findByIdForUpdate(applicationId)
        .orElseThrow(() -> new BusinessException(CompanyApplicationErrorCode.APPLICATION_NOT_FOUND));

    if (app.getStatus() != CompanyApplicationStatus.SUBMITTED) {
      throw new BusinessException(CompanyApplicationErrorCode.INVALID_STATUS);
    }

    // 승인 처리(승인자 기록)
    app.approveNow(reviewerUserId);

    // 회사 생성 + 코드 발급
    CompanyProvisionResult result = companyProvisionService.provisionFromApplication(app);
    Long comId = result.getComId();
    String companyCode = result.getCompanyCode();

    // 기본 역할 생성
    defaultRoleCommandService.ensureDefaults(comId);

    // 첫 사원 생성
    CreateFirstEmpRequest firstEmpReq =
        new CreateFirstEmpRequest(comId, app.getName(), app.getEmail());
    CreateFirstEmpResponse firstEmpRes =
        employeeCommandService.createFirstEmployee(firstEmpReq);
    Long empId = firstEmpRes.getEmpId();

    // 첫 사원 역할 부여
    employeeRoleAssignmentApplicationService.assignForFirstEmployee(comId, empId);

    // 임시 비밀번호 생성
    String tempPassword = RandomGenerator.generateTempPassword(10);

    // 첫 계정 생성
    CreateFirstUserRequest admin =
        userAccountService.createFirstUserRequest(
            comId,
            companyCode,
            empId,
            app.getLoginId(),
            app.getName(),
            app.getEmail(),
            tempPassword
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

  /**
   * 회사 신청 반려 (플랫폼 관리자만 가능)
   */
  @Transactional
  public RejectComAppResponse reject(
      Long applicationId,
      Long reviewerUserId,
      String role,
      String reason
  ) {

    validateAdmin(reviewerUserId, role);

    CompanyApplication app = comAppRepository.findByIdForUpdate(applicationId)
        .orElseThrow(() -> new BusinessException(CompanyApplicationErrorCode.APPLICATION_NOT_FOUND));

    // 상태 가드(승인과 동일하게 SUBMITTED만 처리하도록 통일 권장)
    if (app.getStatus() != CompanyApplicationStatus.SUBMITTED) {
      throw new BusinessException(CompanyApplicationErrorCode.INVALID_STATUS);
    }

    app.rejectNow(reason, reviewerUserId);

    return RejectComAppResponse.builder()
        .appId(app.getAppId())
        .status(app.getStatus())
        .rejectReason(app.getRejectReason())
        .reviewedAt(app.getReviewedAt())
        .build();
  }

  //관리자 권한체크
  private void validateAdmin(Long reviewerUserId, String role) {
    if (reviewerUserId == null || role == null || !"admin".equalsIgnoreCase(role)) {
      throw new BusinessException(UserErrorCode.FORBIDDEN);
    }
  }

}
