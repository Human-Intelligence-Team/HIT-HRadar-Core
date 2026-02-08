package org.hit.hradar.domain.employee.command.application.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.company.command.domain.aggregate.Company;
import org.hit.hradar.domain.company.command.domain.repository.CompanyRepository;
import org.hit.hradar.domain.department.command.domain.aggregate.Department;
import org.hit.hradar.domain.department.command.domain.repository.DepartmentRepository;

import org.hit.hradar.domain.employee.command.application.dto.reponse.EmployeeCsvUploadResponse;
import org.hit.hradar.domain.employee.command.application.dto.request.EmployeeCsvRegisterRequest;
import org.hit.hradar.domain.employee.command.domain.aggregate.Employee;
import org.hit.hradar.domain.employee.command.domain.aggregate.EmploymentType;
import org.hit.hradar.domain.employee.command.domain.aggregate.Gender;
import org.hit.hradar.domain.employee.command.domain.repository.EmployeeRepository;
import org.hit.hradar.domain.positions.command.domain.aggregate.Positions;
import org.hit.hradar.domain.positions.command.domain.repository.PositionRepository;
import org.hit.hradar.domain.rolePermission.RoleErrorCode;
import org.hit.hradar.domain.user.command.domain.aggregate.Account;
import org.hit.hradar.domain.user.command.domain.aggregate.AccountStatus;
import org.hit.hradar.domain.user.command.domain.aggregate.UserRole;
import org.hit.hradar.domain.user.command.domain.repository.AccountRepository;
import org.hit.hradar.global.exception.BusinessException;
import org.hit.hradar.domain.employee.EmployeeErrorCode;
import org.hit.hradar.domain.user.UserErrorCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.regex.Pattern;

/**
 * 사원 CSV 일괄 등록 서비스
 */
@Service
@RequiredArgsConstructor

public class EmployeeCsvRegisterService {

    private final EmployeeRepository employeeRepository;
    private final AccountRepository accountRepository;
    private final DepartmentRepository departmentRepository;
    private final PositionRepository positionRepository;
    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;
    private final org.hit.hradar.domain.rolePermission.command.domain.repository.RoleRepository roleRepository;
    private final org.hit.hradar.domain.rolePermission.command.infrastructure.RoleEmpJpaRepository roleEmpRepository;

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    private static final Pattern DATE_PATTERN = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");

    /**
     * 프론트엔드에서 1차 검증 및 수정된 JSON 데이터를 받아 최종 검증 후 일괄 등록합니다.
     * 유효하지 않은 데이터가 하나라도 있으면 전체 등록을 거부합니다 (Transaction Rollback).
     *
     * @param companyId 회사 ID
     * @param requests  수정된 사원 데이터 리스트
     * @return 등록 결과 (성공 개수)
     */
    @Transactional
    public EmployeeCsvUploadResponse register(Long companyId, List<EmployeeCsvRegisterRequest> requests) {
        if (requests == null || requests.isEmpty()) {
            throw new BusinessException(EmployeeErrorCode.INVALID_CSV_FORMAT, "등록할 데이터가 없습니다.");
        }

        Company company = companyRepository.findByCompanyIdAndIsDeleted(companyId, 'N')
                .orElseThrow(() -> new BusinessException(EmployeeErrorCode.EMPLOYEE_ERROR_CODE));

        List<EmployeeRegisterRequest> registers = new ArrayList<>();
        // 2. 단건 검증 및 변환
        Set<String> csvEmails = new HashSet<>();

        for (int i = 0; i < requests.size(); i++) {
            EmployeeCsvRegisterRequest dto = requests.get(i);

            // 검증 로직에서 예외 발생 시 바로 throw BusinessException 됨
            EmployeeRegisterRequest req = validateAndMap(companyId, dto, csvEmails);
            registers.add(req);

            if (req.email() != null) {
                csvEmails.add(req.email());
            }
        }

        // 3. 일괄 저장
        int successCount = 0;
        for (EmployeeRegisterRequest req : registers) {
            saveEmployeeAndAccount(company, req);
            successCount++;
        }

        return EmployeeCsvUploadResponse.builder()
                .successCount(successCount)
                .totalCount(requests.size())
                .build();
    }

    private void saveEmployeeAndAccount(Company company, EmployeeRegisterRequest req) {
        // 사원 생성
        Employee employee = Employee.builder()
                .comId(company.getCompanyId())
                .deptId(req.deptId())
                .positionId(req.positionId())
                .name(req.name())
                .employeeNo(req.employeeNo()) // 사번 매핑
                .email(req.email())
                .phoneNo(req.phoneNo())
                .hireDate(req.hireDate() != null ? LocalDate.parse(req.hireDate()) : null) // 입사일 선택
                .birth(req.birth() != null ? req.birth().replace("-", "") : null) // YYYY-MM-DD -> YYYYMMDD (8자)
                .gender(mapGender(req.gender()))
                .employmentType(EmploymentType.WORKING) // 기본값 재직
                .build();

        Employee savedEmp = employeeRepository.save(employee);

        // 계정 생성
        // 비밀번호 암호화 (CSV 입력값 사용)
        String encodedPassword = passwordEncoder.encode(req.password());

        Account account = Account.builder()
                .comId(company.getCompanyId())
                .comCode(company.getComCode())
                .empId(savedEmp.getEmpId())
                .loginId(req.loginId()) // CSV 입력값 사용
                .email(req.email())
                .name(req.name())
                .password(encodedPassword)
                .userRole(UserRole.user) // 기본 권한
                .status(AccountStatus.ACTIVE)
                .isDeleted('N')
                .build();

        accountRepository.save(account);

        // 기본 권한 부여 (EMPLOYEE)
        org.hit.hradar.domain.rolePermission.command.domain.aggregate.Role defaultRole = roleRepository
                .findByComIdAndRoleKey(company.getCompanyId(), "EMPLOYEE")
                .orElseThrow(() -> new BusinessException(RoleErrorCode.ROLE_NOT_FOUND));

        org.hit.hradar.domain.rolePermission.command.domain.aggregate.EmployeeRole employeeRole = org.hit.hradar.domain.rolePermission.command.domain.aggregate.EmployeeRole
                .builder()
                .roleId(defaultRole.getRoleId())
                .empId(savedEmp.getEmpId())
                .build();

        roleEmpRepository.save(employeeRole);
    }

    private EmployeeRegisterRequest validateAndMap(Long companyId, EmployeeCsvRegisterRequest dto,
            Set<String> csvEmails) {
        String name = dto.getName();
        String email = dto.getEmail();
        String phoneNo = dto.getPhoneNo();
        String employeeNo = dto.getEmployeeNo();
        String loginId = dto.getLoginId();
        String password = dto.getPassword();
        String hireDate = dto.getHireDate();

        String deptName = dto.getDeptName();
        String positionName = dto.getPositionName();
        String gender = dto.getGender();
        String birth = dto.getBirth();

        // 필수값
        if (isEmpty(name))
            throw new BusinessException(EmployeeErrorCode.CSV_MISSING_REQUIRED_FIELD, "이름은 필수입니다.");
        if (isEmpty(email))
            throw new BusinessException(UserErrorCode.EMAIL_REQUIRED);
        if (isEmpty(phoneNo))
            throw new BusinessException(EmployeeErrorCode.CSV_MISSING_REQUIRED_FIELD, "전화번호는 필수입니다.");
        if (isEmpty(employeeNo))
            throw new BusinessException(EmployeeErrorCode.CSV_MISSING_REQUIRED_FIELD, "사번은 필수입니다.");
        if (isEmpty(loginId))
            throw new BusinessException(EmployeeErrorCode.CSV_MISSING_REQUIRED_FIELD, "로그인ID는 필수입니다.");
        if (isEmpty(password))
            throw new BusinessException(EmployeeErrorCode.CSV_MISSING_REQUIRED_FIELD, "비밀번호는 필수입니다.");
        if (isEmpty(gender))
            throw new BusinessException(EmployeeErrorCode.CSV_MISSING_REQUIRED_FIELD, "성별은 필수입니다.");
        if (isEmpty(birth))
            throw new BusinessException(EmployeeErrorCode.CSV_MISSING_REQUIRED_FIELD, "생년월일은 필수입니다.");
        // hireDate는 선택

        // 포맷
        if (!EMAIL_PATTERN.matcher(email).matches())
            throw new BusinessException(EmployeeErrorCode.CSV_INVALID_FORMAT, "이메일 형식이 올바르지 않습니다.");
        if (!isEmpty(hireDate) && !DATE_PATTERN.matcher(hireDate).matches())
            throw new BusinessException(EmployeeErrorCode.CSV_INVALID_FORMAT, "입사일 형식이 올바르지 않습니다(YYYY-MM-DD).");
        if (!isEmpty(birth) && !DATE_PATTERN.matcher(birth).matches())
            throw new BusinessException(EmployeeErrorCode.CSV_INVALID_FORMAT, "생년월일 형식이 올바르지 않습니다.");
        if (!isEmpty(gender) && !("M".equalsIgnoreCase(gender) || "F".equalsIgnoreCase(gender)))
            throw new BusinessException(EmployeeErrorCode.CSV_INVALID_FORMAT, "성별 형식이 올바르지 않습니다(M/F).");

        // 중복
        if (csvEmails.contains(email))
            throw new BusinessException(UserErrorCode.EMAIL_ALREADY_EXISTS, "파일 내 중복된 이메일입니다: " + email);
        if (employeeRepository.existsByEmailAndComIdAndIsDeleted(email, companyId, 'N')) {
            throw new BusinessException(UserErrorCode.EMAIL_ALREADY_EXISTS, "이미 가입된 이메일입니다: " + email);
        }
        if (employeeRepository.existsByEmployeeNoAndComIdAndIsDeleted(employeeNo, companyId, 'N')) {
            throw new BusinessException(EmployeeErrorCode.DUPLICATE_EMPLOYEE_NO_OR_EMAIL,
                    "이미 존재하는 사번입니다: " + employeeNo);
        }
        if (accountRepository.existsByLoginIdAndComIdAndIsDeleted(loginId, companyId, 'N')) {
            throw new BusinessException(UserErrorCode.DUPLICATE_LOGIN_ID, "이미 존재하는 로그인ID입니다: " + loginId);
        }

        // 부서 ID 매핑
        Long deptId = null;
        if (!isEmpty(deptName)) {
            Department dept = departmentRepository.findByDeptNameAndComIdAndIsDeleted(deptName, companyId, 'N')
                    .orElseThrow(() -> new BusinessException(EmployeeErrorCode.INVALID_DEPARTMENT,
                            "존재하지 않는 부서명입니다: " + deptName));
            deptId = dept.getDeptId();
        }

        // 직위 ID 매핑
        Long positionId = null;
        if (!isEmpty(positionName)) {
            Positions pos = positionRepository.findByNameAndComIdAndIsDeleted(positionName, companyId, 'N')
                    .orElseThrow(() -> new BusinessException(EmployeeErrorCode.INVALID_POSITION,
                            "존재하지 않는 직위명입니다: " + positionName));
            positionId = pos.getPositionId();
        }

        return new EmployeeRegisterRequest(name, email, phoneNo, employeeNo, loginId, password,
                isEmpty(hireDate) ? null : hireDate, // 빈 문자열을 null로 변환
                deptId,
                positionId,
                isEmpty(gender) ? null : gender, // 빈 문자열을 null로 변환
                isEmpty(birth) ? null : birth); // 빈 문자열을 null로 변환
    }

    private Gender mapGender(String genderStr) {
        if (isEmpty(genderStr))
            return null;
        String upper = genderStr.toUpperCase();
        if ("M".equals(upper) || "MALE".equals(upper))
            return Gender.MALE;
        if ("F".equals(upper) || "FEMALE".equals(upper))
            return Gender.FEMALE;
        return null; // 또는 기본값 처리
    }

    private boolean isEmpty(String str) {
        return str == null || str.isBlank();
    }

    // 내부 DTO
    private record EmployeeRegisterRequest(
            String name, String email, String phoneNo, String employeeNo, String loginId, String password,
            String hireDate,
            Long deptId, Long positionId, String gender, String birth) {
    }
}
