package org.hit.hradar.domain.employee.command.application.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.department.command.domain.repository.DepartmentRepository;
import org.hit.hradar.domain.employee.EmployeeErrorCode;
import org.hit.hradar.domain.employee.command.application.dto.CsvImportEmployeesResponse;
import org.hit.hradar.domain.employee.command.application.dto.CsvImportFailure;
import org.hit.hradar.domain.employee.command.application.dto.EmployeeAccountCsvRow;
import org.hit.hradar.domain.employee.command.domain.aggregate.Employee;
import org.hit.hradar.domain.employee.command.domain.repository.EmployeeRepository;
import org.hit.hradar.domain.positions.command.domain.repository.PositionRepository;
import org.hit.hradar.domain.user.UserErrorCode;
import org.hit.hradar.domain.user.command.domain.aggregate.Account;
import org.hit.hradar.domain.user.command.domain.aggregate.AccountStatus;
import org.hit.hradar.domain.user.command.domain.repository.UserRepository;
import org.hit.hradar.domain.user.command.infrastructure.UserJpaRepository;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * 목적:
 *  - CSV 업로드(사원정보 + 계정정보) 1번으로
 *    Employee 테이블과 UserAccount(계정) 테이블을 "동시에" 생성한다.
 *
 * 핵심 정책:
 *  - CSV에서 loginId/password를 사용자가 직접 적는다 (서버가 임의 생성하지 않음)
 *  - 검증 실패한 row는 실패 목록에 담고 다음 row로 진행 (부분 성공 허용)
 *    -> 원하면 "전체 롤백" 방식으로 쉽게 변경 가능(아래 주석 참고)
 *
 * 주의:
 *  - 이 코드는 split(",")로 단순 파싱한다.
 *    값에 콤마가 포함되거나 따옴표로 감싼 CSV는 깨질 수 있음.
 *    운영 수준이면 commons-csv 같은 라이브러리로 교체 권장.
 */
@Service
@RequiredArgsConstructor
public class EmployeeOnboardingCommandService {

  // ====== Command Repositories ======
  private final EmployeeRepository employeeRepository;
  private final UserRepository userRepository;
  private final UserJpaRepository userJpaRepository;

  // ====== Validation을 위한 참조 Repo (부서/직위 존재 여부 체크) ======
  private final DepartmentRepository departmentRepository;
  private final PositionRepository positionRepository;

  // ====== 비밀번호는 반드시 해시(암호화) 저장해야 함 ======
  private final PasswordEncoder passwordEncoder;

  /**
   * CSV 업로드 → Employee + UserAccount 생성
   *
   * @param file  업로드된 CSV 파일 (multipart)
   * @param comId 현재 로그인 유저의 회사 ID (tenant scope)
   * @return 성공/실패 집계 + 실패 row 상세 이유 목록
   */
  @Transactional
  public CsvImportEmployeesResponse importEmployeesAndAccountsFromCsv(MultipartFile file, Long comId) {

    // 실패 row들을 모아두는 리스트 (부분 성공 정책을 위해 필요)
    List<CsvImportFailure> failures = new ArrayList<>();

    // 처리한 row 수(빈 줄 제외)
    int total = 0;

    // 정상적으로 Employee+Account 모두 생성된 row 수
    int success = 0;

    // 1) 파일을 라인 단위로 읽는다.
    //    - BufferedReader로 UTF-8 인코딩 기준 읽기
    //    - lines()를 통해 전체 라인을 List<String>으로 변환
    List<String> lines = readAllLines(file);

    // 2) 헤더가 있으면 첫 줄을 스킵한다.
    //    - 매우 단순한 감지 로직: 첫줄에 "employeeNo" 같은 컬럼명이 들어있으면 헤더로 판단
    //    - 팀 내 CSV 포맷을 확정하면 더 명확하게: 첫줄이 정확히 "name,employeeNo,..."인지로 비교하는 방법 추천
    int startIndex = 0;
    if (!lines.isEmpty() && lines.get(0).toLowerCase().contains("employeeno")) {
      startIndex = 1;
    }

    // 3) 실제 데이터 라인들을 순회하며 row 단위로 처리한다.
    for (int i = startIndex; i < lines.size(); i++) {

      String line = lines.get(i);
      int rowNumber = i + 1; // 사람이 보는 CSV 줄 번호(1부터)

      // 완전 빈 줄은 무시 (total에도 카운트하지 않음)
      if (line == null || line.isBlank()) {
        continue;
      }

      total++;

      try {
        // 3-1) 한 줄을 파싱해서 DTO(EmployeeAccountCsvRow)로 변환
        //      - 여기에서 컬럼 개수 부족, 숫자/날짜 파싱 실패 등이 발생할 수 있음
        EmployeeAccountCsvRow row = parseLineToRow(line);

        // 3-2) row 내용 검증
        //      - 필수값(이름/사번/이메일/loginId/password)
        //      - deptId/positionId가 있으면 존재하는지
        //      - employeeNo/email/loginId가 회사(comId) 범위에서 중복인지
        validateRow(row, comId);

        // 3-3) Employee 생성
        //      - 테넌트 분리를 위해 comId는 반드시 서버에서 주입 (row에서 받지 않는 게 안전)
        Employee employee = Employee.builder()
            .deptId(row.getDeptId())
            .positionId(row.getPositionId())
            .name(row.getName())
            .employeeNo(row.getEmployeeNo())
            .email(row.getEmail())
            .hireDate(row.getHireDate())
            .phoneNo(row.getPhoneNo())
            .build();

        // save() 호출 시 PK(empId)가 생성된다.
        Employee savedEmp = employeeRepository.save(employee);

        // 3-4) UserAccount 생성
        //      - 비밀번호는 반드시 해시로 저장 (평문 저장 금지)
        String encodedPw = passwordEncoder.encode(row.getPassword());

        // 계정이 사원에 종속되는 구조라면 empId를 FK로 저장한다.
        // (구조가 다르면 이 부분을 제거/수정)
        Account account = Account.builder()
            .comId(comId)
            .empId(savedEmp.getEmpId())
            .loginId(row.getLoginId())
            .password(encodedPw)
            .build();

        userRepository.save(account);

        // 3-5) (선택) 기본 역할(RoleEmp) 부여
        //      - CSV 업로드 시 "기본 역할(예: EMPLOYEE)"을 자동으로 붙이려면 여기에서 처리
        // roleEmpRepository.save(RoleEmp.create(savedEmp.getEmpId(), defaultRoleId, ...));

        // 3-6) 여기까지 오면 row 1개가 "완전 성공"
        success++;

      } catch (Exception e) {
        // 부분 성공 정책:
        // - 해당 row만 실패로 기록하고 다음 row로 계속 진행
        // - 실패 이유를 프론트에 내려줘서 사용자가 CSV를 수정할 수 있게 한다.
        failures.add(CsvImportFailure.builder()
            .rowNumber(rowNumber)
            .reason(extractReason(e))
            .rawLine(line)
            .build());

        // continue (명시 안 해도 다음 loop로 진행)
      }
    }

    // 4) 집계 결과 반환
    return CsvImportEmployeesResponse.builder()
        .total(total)
        .success(success)
        .failed(failures.size())
        .failures(failures)
        .build();

    /*
     * ===== 전체 롤백 방식으로 바꾸고 싶으면? =====
     *  - try/catch를 제거하거나 catch에서 다시 throw 하면 됨.
     *  - 즉, 실패 row가 하나라도 있으면 트랜잭션 전체가 롤백되어
     *    "모두 성공하거나 모두 실패"가 된다.
     *
     * 장단점:
     *  - 부분 성공: 현업 CSV 업로드에서 일반적(대량 업로드 시 일부 오류만 수정)
     *  - 전체 롤백: 데이터 일관성은 강하지만, 한 줄 실수로 전체 재업로드 필요
     */
  }

  /**
   * 파일을 라인 단위로 전부 읽어서 List<String>으로 반환.
   * - 예외 발생 시 INVALID_CSV_FILE 같은 에러코드로 BusinessException 처리
   */
  private List<String> readAllLines(MultipartFile file) {
    try (BufferedReader br =
             new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {

      // Java Stream을 통해 모든 라인을 한 번에 수집
      return br.lines().toList();

    } catch (Exception e) {
      // 파일이 깨졌거나 인코딩 문제/IO 에러 등
      throw new BusinessException(EmployeeErrorCode.INVALID_CSV_FILE);
    }
  }

  /**
   * 매우 단순한 CSV 파서 (split 기반)
   *
   * 기대 컬럼 순서(권장):
   * name,employeeNo,email,deptId,positionId,gender,birth,hireDate,phoneNo,loginId,password
   *
   * 주의:
   * - 값 안에 콤마가 들어가면 split(",")로는 파싱 불가
   * - "홍,길동" 같은 형태는 운영에서 반드시 깨짐
   * - 운영급이면 commons-csv(따옴표/escape 지원)로 교체 권장
   */
  private EmployeeAccountCsvRow parseLineToRow(String line) {

    // -1 옵션: 뒤쪽 빈 컬럼도 유지한다.
    // 예) "a,b,c," -> 마지막 컬럼을 ""로 유지해서 컬럼 개수가 맞게 됨
    String[] c = line.split(",", -1);

    // 필요한 컬럼 수가 부족하면 포맷 오류
    if (c.length < 11) {
      throw new BusinessException(EmployeeErrorCode.INVALID_CSV_FORMAT);
    }

    // 각 컬럼을 trim하고 빈 문자열은 null로 변환
    // deptId/positionId는 숫자이므로 Long 파싱
    // birth/hireDate는 yyyy-MM-dd 포맷 LocalDate 파싱
    return EmployeeAccountCsvRow.builder()
        .name(trimToNull(c[0]))
        .employeeNo(trimToNull(c[1]))
        .email(trimToNull(c[2]))
        .deptId(parseLongOrNull(c[3]))
        .positionId(parseLongOrNull(c[4]))
        .gender(trimToNull(c[5]))
        .birth(parseDateOrNull(c[6]))
        .hireDate(parseDateOrNull(c[7]))
        .phoneNo(trimToNull(c[8]))
        .loginId(trimToNull(c[9]))
        .password(trimToNull(c[10]))
        .build();
  }

  /**
   * row 검증 로직
   *
   * 검증의 목적:
   * - DB에 insert 하기 전에 "비즈니스 룰" 위반을 선제 차단
   * - 실패 이유를 사용자에게 설명 가능한 형태로 제공
   */
  private void validateRow(EmployeeAccountCsvRow row, Long comId) {

    // 1) 필수값 검증: 사원 기본 정보
    if (row.getName() == null || row.getEmployeeNo() == null || row.getEmail() == null) {
      throw new BusinessException(EmployeeErrorCode.INVALID_CSV_ROW_REQUIRED);
    }

    // 2) 필수값 검증: 계정 정보
    //    - 로그인ID/비밀번호는 사용자가 작성하는 정책이므로 둘 다 필수 처리
    if (row.getLoginId() == null || row.getPassword() == null) {
      throw new BusinessException(EmployeeErrorCode.INVALID_CSV_ROW_ACCOUNT_REQUIRED);
    }

    // 3) 참조 무결성 검증: deptId가 있으면 해당 회사의 부서가 존재해야 함
    if (row.getDeptId() != null) {
      departmentRepository.findByDeptIdAndCompanyIdAndIsDeleted(row.getDeptId(), comId, 'N')
          .orElseThrow(() -> new BusinessException(EmployeeErrorCode.INVALID_DEPARTMENT));
    }

    // 4) 참조 무결성 검증: positionId가 있으면 해당 회사의 직위가 존재해야 함
    if (row.getPositionId() != null) {
      positionRepository.findByPositionIdAndComIdAndIsDeleted(row.getPositionId(), comId, 'N')
          .orElseThrow(() -> new BusinessException(EmployeeErrorCode.INVALID_POSITION));
    }

    // 5) 중복 검증: 사번/이메일 (회사 범위 내)
    boolean dupEmp =
        employeeRepository.existsByEmployeeNoAndComIdAndIsDeleted(row.getEmployeeNo(), comId, 'N')
            || employeeRepository.existsByEmailAndComIdAndIsDeleted(row.getEmail(), comId, 'N');

    if (dupEmp) {
      throw new BusinessException(EmployeeErrorCode.DUPLICATE_EMPLOYEE_NO_OR_EMAIL);
    }

    // 6) 중복 검증: loginId (회사 범위 내)
    if (userJpaRepository.existsByComIdAndLoginIdAndStatus(comId, row.getLoginId(), AccountStatus.ACTIVE)) {
      throw new BusinessException(UserErrorCode.DUPLICATE_LOGIN_ID);
    }

  }

  // =========================
  // 아래는 파싱/에러 메시지 유틸
  // =========================

  /**
   * 문자열 양쪽 공백 제거 후
   * - 빈 문자열이면 null 반환
   * - 값이 있으면 trim된 값 반환
   */
  private String trimToNull(String s) {
    if (s == null) return null;
    String t = s.trim();
    return t.isEmpty() ? null : t;
  }

  /**
   * Long 파싱:
   * - 빈 값이면 null
   * - 값이 있으면 Long.parseLong
   * - 숫자 형식이 아니면 NumberFormatException 발생 -> 상위에서 failure 처리
   */
  private Long parseLongOrNull(String s) {
    String t = trimToNull(s);
    if (t == null) return null;
    return Long.parseLong(t);
  }

  /**
   * LocalDate 파싱:
   * - 빈 값이면 null
   * - 값이 있으면 LocalDate.parse(yyyy-MM-dd)
   * - 포맷이 다르면 DateTimeParseException 발생 -> 상위에서 failure 처리
   */
  private java.time.LocalDate parseDateOrNull(String s) {
    String t = trimToNull(s);
    if (t == null) return null;
    return java.time.LocalDate.parse(t); // yyyy-MM-dd
  }

  /**
   * 실패 사유를 사용자에게 보여주기 위한 문자열로 변환
   * - BusinessException이면 메시지(또는 코드명)를 내려주고
   * - 그 외 예외는 클래스명 + 메시지로 최소 정보 제공
   *
   * 참고:
   * - 지금은 getMessage()를 쓰지만,
   *   프론트가 에러코드 기반으로 번역/표시한다면
   *   BusinessException에서 ErrorCode를 꺼내 내려주는 방식이 더 좋다.
   */
  private String extractReason(Exception e) {
    if (e instanceof BusinessException be) {
      return be.getMessage();
    }
    return e.getClass().getSimpleName() + ": " + e.getMessage();
  }
}
