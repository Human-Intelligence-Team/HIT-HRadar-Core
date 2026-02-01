package org.hit.hradar.domain.employee.command.application.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.company.command.domain.aggregate.Company;
import org.hit.hradar.domain.company.command.domain.repository.CompanyRepository;
import org.hit.hradar.domain.employee.EmployeeErrorCode;
import org.hit.hradar.domain.employee.command.application.dto.request.CreateEmployeeWithAccountRequest;
import org.hit.hradar.domain.employee.command.application.dto.reponse.CreateEmployeeWithAccountResponse;
import org.hit.hradar.domain.employee.command.domain.aggregate.Employee;
import org.hit.hradar.domain.employee.command.domain.repository.EmployeeRepository;
import org.hit.hradar.domain.user.UserErrorCode;
import org.hit.hradar.domain.employee.command.domain.aggregate.EmploymentType;
import org.hit.hradar.domain.employee.command.domain.aggregate.Gender;
import org.hit.hradar.domain.user.command.domain.aggregate.Account;
import org.hit.hradar.domain.user.command.domain.aggregate.AccountStatus;
import org.hit.hradar.domain.user.command.domain.aggregate.UserRole;
import org.hit.hradar.domain.user.command.domain.repository.AccountRepository;
import org.hit.hradar.domain.user.command.infrastructure.AccountJpaRepository;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class EmployeeAccountCommandService {

  private final EmployeeRepository employeeRepository;
  private final AccountRepository accountRepository;
  private final AccountJpaRepository userJpaRepository;
  private final CompanyRepository companyRepository;
  private final PasswordEncoder passwordEncoder;

  /**
   * 사원 + 계정 생성
   * - 사원 먼저 저장 후, 해당 empId로 계정 저장
   */
  @Transactional
  public CreateEmployeeWithAccountResponse createEmployeeWithAccount(
      Long comId,
      CreateEmployeeWithAccountRequest request) {

    if (comId == null) {
      throw new BusinessException(UserErrorCode.FORBIDDEN);
    }

    Company company = companyRepository.findByCompanyIdAndIsDeleted(comId, 'N')
        .orElseThrow(() -> new BusinessException(EmployeeErrorCode.EMPLOYEE_ERROR_CODE)); // 회사 정보 없음

    // 1) 사원 중복 체크: (회사 내) 사번 유니크
    if (employeeRepository.existsByEmployeeNoAndComIdAndIsDeleted(
        request.getEmployeeNo(), comId, 'N')) {
      throw new BusinessException(EmployeeErrorCode.DUPLICATE_EMPLOYEE_NO_OR_EMAIL);
    }

    // 2) 계정 중복 체크: (회사 내) loginId 유니크
    if (userJpaRepository.existsByComIdAndLoginIdAndStatus(
        comId, request.getLoginId(), AccountStatus.ACTIVE)) {
      throw new BusinessException(UserErrorCode.DUPLICATE_LOGIN_ID);
    }

    // 3) 이메일 중복 체크(정책상 이메일을 유니크로 볼 때만)
    if (request.getEmail() != null
        && userJpaRepository.existsByComIdAndEmailAndStatus(comId, request.getEmail(), AccountStatus.ACTIVE)) {
      throw new BusinessException(UserErrorCode.EMAIL_ALREADY_EXISTS);
    }

    // 4) 사원 생성/저장
    Employee savedEmployee = employeeRepository.save(
        Employee.builder()
            .comId(comId)
            .employeeNo(request.getEmployeeNo())
            .name(request.getName())
            .email(request.getEmail())
            .phoneNo(request.getPhone())
            .deptId(request.getDeptId())
            .positionId(request.getPositionId())
            .gender(mapGender(request.getGender()))
            .birth((request.getBirth() != null && !request.getBirth().isBlank()) ? request.getBirth().replace("-", "")
                : null)
            .hireDate((request.getHireDate() != null && !request.getHireDate().isBlank())
                ? LocalDate.parse(request.getHireDate())
                : null)
            .employmentType(EmploymentType.WORKING) // 기본값 재직
            .build());

    // 5) 비밀번호는 입력받은 값을 해시해서 저장
    String encodedPw = passwordEncoder.encode(request.getPassword());

    // 6) 계정 생성/저장 (empId 연결)
    Account savedAccount = accountRepository.save(
        Account.builder()
            .comId(comId)
            .comCode(company.getComCode()) // missing comCode 추가
            .empId(savedEmployee.getEmpId()) // 사원 FK
            .loginId(request.getLoginId())
            .name(request.getName())
            .email(request.getEmail())
            .password(encodedPw)
            .status(AccountStatus.ACTIVE)
            .userRole(UserRole.user)
            .isDeleted('N')
            .build());

    // 7) 응답(비밀번호는 반환하지 않음)
    return CreateEmployeeWithAccountResponse.builder()
        .empId(savedEmployee.getEmpId())
        .accId(savedAccount.getAccId())
        .employeeNo(savedEmployee.getEmployeeNo())
        .loginId(savedAccount.getLoginId())
        .build();
  }

  private Gender mapGender(String genderStr) {
    if (genderStr == null || genderStr.isBlank())
      return null;
    String upper = genderStr.toUpperCase();
    if ("M".equals(upper) || "MALE".equals(upper))
      return Gender.MALE;
    if ("F".equals(upper) || "FEMALE".equals(upper))
      return Gender.FEMALE;
    return null;
  }
}
