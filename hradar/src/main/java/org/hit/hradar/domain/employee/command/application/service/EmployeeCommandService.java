package org.hit.hradar.domain.employee.command.application.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.employee.EmployeeErrorCode;
import org.hit.hradar.domain.employee.command.application.dto.reponse.UpdateEmployeeProfileResponse;
import org.hit.hradar.domain.employee.command.application.dto.request.CreateFirstEmpRequest;
import org.hit.hradar.domain.employee.command.application.dto.reponse.CreateFirstEmpResponse;
import org.hit.hradar.domain.employee.command.application.dto.request.UpdateEmployeeProfileRequest;
import org.hit.hradar.domain.employee.command.domain.aggregate.Employee;
import org.hit.hradar.domain.employee.command.domain.repository.EmployeeRepository;
import org.hit.hradar.domain.user.UserErrorCode;
import org.hit.hradar.domain.user.command.domain.aggregate.Account;
import org.hit.hradar.domain.user.command.domain.repository.AccountRepository;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmployeeCommandService {

  private final EmployeeRepository employeeRepository;
  private final AccountRepository accountRepository;

  /**
   * 회사 생성자(첫 사원)
   * - defaultRoleId는 상위 서비스에서 결정해서 전달
   * - additionalRoleId(OWNER)도 상위 서비스에서 결정해서 전달
   */
  @Transactional
  public CreateFirstEmpResponse createFirstEmployee(CreateFirstEmpRequest req) {

    Employee emp = Employee.builder()
        .comId(req.getComId())
        .deptId(null)
        .positionId(null)
        .name(req.getName())
        .email(req.getEmail())
        .employeeNo(null)
        .build();

    Employee saved = employeeRepository.save(emp);

    return CreateFirstEmpResponse.builder()
        .empId(saved.getEmpId())
        .comId(saved.getComId())
        .build();
  }

  @Transactional
  public UpdateEmployeeProfileResponse updateProfile(Long comId, Long empId, UpdateEmployeeProfileRequest req) {

    Employee emp = employeeRepository.findByEmpIdAndComIdAndIsDeleted(empId, comId, 'N')
        .orElseThrow(() -> new BusinessException(EmployeeErrorCode.EMPLOYEE_ERROR_CODE));

    // name 필수
    String name = (req.getName() == null) ? null : req.getName().trim();
    if (name == null || name.isEmpty()) {
      throw new BusinessException(EmployeeErrorCode.EMPLOYEE_ERROR_CODE); // 너희 코드에 맞는 VALIDATION 에러코드로 교체 권장
    }

    // email 정규화 (blank면 null로)
    String email = (req.getEmail() == null || req.getEmail().trim().isEmpty()) ? null : req.getEmail().trim();

    // email 유니크 체크(새 값이 있고, 변경되는 경우에만)
    if (email != null && (emp.getEmail() == null || !email.equals(emp.getEmail()))) {
      if (employeeRepository.existsByEmailAndComIdAndIsDeleted(email, comId, 'N')) {
        throw new BusinessException(EmployeeErrorCode.DUPLICATE_EMPLOYEE_NO_OR_EMAIL);
      }
    }

    String birth = (req.getBirth() != null && !req.getBirth().isBlank()) ? req.getBirth().replace("-", "") : null;

    emp.updateEmployee(
        name,
        email,
        req.getGender(),
        birth,
        req.getHireDate(),
        req.getExitDate(),
        req.getImage(),
        req.getExtNo(),
        req.getPhoneNo());

    return UpdateEmployeeProfileResponse.builder()
        .empId(emp.getEmpId())
        .name(emp.getName())
        .email(emp.getEmail())
        .gender(emp.getGender())
        .birth(emp.getBirth())
        .hireDate(emp.getHireDate())
        .exitDate(emp.getExitDate())
        .image(emp.getImage())
        .extNo(emp.getExtNo())
        .phoneNo(emp.getPhoneNo())
        .build();
  }

  @Transactional
  public void deleteEmployee(Long empId, Long comId) {

    Employee employee = employeeRepository.findByEmpIdAndComIdAndIsDeleted(empId, comId, 'N')
        .orElseThrow(() -> new BusinessException(EmployeeErrorCode.EMPLOYEE_ERROR_CODE));

    // 계정 비활성 + soft delete
    Account account = accountRepository.findByEmpIdAndComIdAndIsDeleted(empId, comId, 'N')
        .orElseThrow(() -> new BusinessException(UserErrorCode.USER_NOT_FOUND));
    account.isDeleted();

    // 사원 soft delete
    employee.deletedEmployee();
  }

}