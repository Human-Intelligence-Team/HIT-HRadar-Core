package org.hit.hradar.domain.user.command.application.service;


import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.company.command.domain.aggregate.Company;
import org.hit.hradar.domain.company.command.domain.repository.CompanyRepository;
import org.hit.hradar.domain.companyApplication.CompanyApplicationErrorCode;
import org.hit.hradar.domain.companyApplication.command.application.dto.ApproveComAppResponse;
import org.hit.hradar.domain.companyApplication.command.domain.aggregate.CompanyApplication;
import org.hit.hradar.domain.companyApplication.command.domain.aggregate.CompanyApplicationStatus;
import org.hit.hradar.domain.companyApplication.command.domain.repository.ComAppCommandRepository;
import org.hit.hradar.domain.employee.command.domain.repository.EmployeeRepository;
import org.hit.hradar.domain.user.UserErrorCode;
import org.hit.hradar.domain.user.command.domain.aggregate.AccountStatus;
import org.hit.hradar.domain.user.command.domain.aggregate.UserAccount;
import org.hit.hradar.domain.user.command.domain.aggregate.UserRole;
import org.hit.hradar.domain.user.command.domain.repository.UserRepository;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class AdminService {

  private final ComAppCommandRepository appRepo;
  private final CompanyRepository companyRepository;
  private final RoleRepository roleRepository;
  private final UserRepository userRepository;
  private final EmployeeRepository employeeRepository;
  private final EmployeeRoleRepository employeeRoleRepository;
  private final PasswordEncoder passwordEncoder;

  public ApproveComAppResponse approve(Long applicationId) {

    //회사 생성 요청이 존재 하는가?
    CompanyApplication app = appRepo.findByIdForUpdate(applicationId)
        .orElseThrow(() -> new BusinessException(CompanyApplicationErrorCode.APPLICATION_NOT_FOUND));
    // 회사가 승인된 상태가 맞는가?
    if (app.getStatus() != CompanyApplicationStatus.APPROVED) {
      throw new BusinessException(CompanyApplicationErrorCode.INVALID_STATUS);
    }
    //회사생성
    String companyCode = CompanyCodeGenerator.generate();
    Company company = companyRepository.save(
        Company.builder()
            .comCode(companyCode)
            .name(app.getComName())
            .ceoName(app.getCeoName())
            .bizNo(app.getBizNo())
            .comTel(app.getComTel())
            .address(app.getAddress())
            .build()
    );

    //회사의 기본역할 생성(회사관리자, 인사팀, 팀장, 사원 - 권한 매핑)

    // 임시 비밀번호 자동 생성(평문 저장 금지)
    String tempPassword = PasswordGenerator.generate(12);
    String encoded = passwordEncoder.encode(tempPassword);

    // 최고관리자 계정 생성
    UserAccount adminAccount = userRepository.save(
        UserAccount.builder()
            .loginId(app.getComAdminLoginId())
            .password(encoded)
            .userRole(UserRole.user)
            .status(AccountStatus.ACTIVE)
            .build()
    );

    // employee 생성 (최고관리자도 employee에 포함, 사번 수정가능)
    Employee adminEmployee = employeeRepository.save(
        Employee.builder()
            .companyId(company.getCompanyId())
            .accId(adminAccount.getAccId())
            .employeeNo("ADMIN-0001")
            .employeeName(app.getComAdminName())
            .build()
    );

    // 최고관리자 role 부여
    employeeRoleRepository.save(
        EmployeeRole.builder()
            .empId(adminEmployee.getEmpId())
            .roleId(superAdminRole.getRoleId())
            .build()
    );

    app.approve(java.time.LocalDateTime.now());

    return ApproveCompanyApplicationResponse.builder()
        .companyId(company.getCompanyId())
        .companyCode(companyCode)
        .superAdminLoginId(app.getComAdminLoginId())
        .tempPassword(tempPassword) // 플랫폼관리자가 확인하고 “서면 전달”
        .build();
  }
}



