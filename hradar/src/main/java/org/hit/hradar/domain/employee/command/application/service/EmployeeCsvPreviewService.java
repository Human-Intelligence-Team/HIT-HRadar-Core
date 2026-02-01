package org.hit.hradar.domain.employee.command.application.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.department.command.domain.repository.DepartmentRepository;
import org.hit.hradar.domain.document.command.domain.application.csv.CsvParseResult;
import org.hit.hradar.domain.document.command.domain.application.csv.CsvParser;
import org.hit.hradar.domain.employee.command.application.dto.reponse.EmployeeCsvPreviewResponse;
import org.hit.hradar.domain.employee.command.application.dto.reponse.EmployeeCsvPreviewRow;
import org.hit.hradar.domain.employee.command.domain.repository.EmployeeRepository;
import org.hit.hradar.domain.positions.command.domain.repository.PositionRepository;
import org.hit.hradar.domain.user.command.domain.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.hit.hradar.global.exception.BusinessException;
import org.hit.hradar.domain.employee.EmployeeErrorCode;

import java.util.*;
import java.util.regex.Pattern;

/**
 * 사원 CSV 업로드 미리보기 및 검증 서비스
 */
@Service
@RequiredArgsConstructor
public class EmployeeCsvPreviewService {

    private final CsvParser csvParser;
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final PositionRepository positionRepository;
    private final AccountRepository accountRepository;

    // 이메일 정규식 (간단 버전)
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    // 날짜 형식 (YYYY-MM-DD)
    private static final Pattern DATE_PATTERN = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");

    /**
     * CSV 파일을 파싱하고 유효성을 검증하여 미리보기 결과를 반환합니다.
     *
     * @param companyId 회사 ID
     * @param file      업로드된 CSV 파일
     * @return 미리보기 결과 (유효성 검사 포함)
     */
    @Transactional(readOnly = true)
    public EmployeeCsvPreviewResponse preview(Long companyId, MultipartFile file) {
        // 1. CSV 파싱
        CsvParseResult parseResult;
        try {
            parseResult = csvParser.parse(file);
        } catch (Exception e) {
            throw new BusinessException(EmployeeErrorCode.INVALID_CSV_FORMAT,
                    "CSV 파일 파싱 중 오류가 발생했습니다: " + e.getMessage());
        }

        Map<String, Integer> header = parseResult.getHeaderIndex();
        List<String[]> rows = parseResult.getRows();

        List<EmployeeCsvPreviewRow> previewRows = new ArrayList<>();
        int validCount = 0;
        int invalidCount = 0;

        // CSV 내 중복 체크용 Set
        Set<String> csvEmails = new HashSet<>();
        Set<String> csvEmployeeNos = new HashSet<>();
        Set<String> csvLoginIds = new HashSet<>();

        // 2. 각 행 검증
        for (int i = 0; i < rows.size(); i++) {
            String[] rowData = rows.get(i);
            int rowNumber = i + 2; // 헤더가 1행이므로 데이터는 2행부터 시작

            EmployeeCsvPreviewRow previewRow;
            try {
                // 값 추출 (헤더 매핑)
                String name = getValue(header, rowData, "이름");
                String email = getValue(header, rowData, "이메일");
                String phoneNo = getValue(header, rowData, "전화번호");
                String employeeNo = getValue(header, rowData, "사번");
                String loginId = getValue(header, rowData, "로그인ID");
                String password = getValue(header, rowData, "비밀번호");
                String hireDate = getValue(header, rowData, "입사일");
                String deptName = getValue(header, rowData, "부서명");
                String positionName = getValue(header, rowData, "직위명");
                String gender = getValue(header, rowData, "성별");
                String birth = getValue(header, rowData, "생년월일");

                // 유효성 검사 수행
                ValidationResult validation = validateRow(companyId, name, email, phoneNo, employeeNo, loginId,
                        password,
                        hireDate, deptName, positionName,
                        gender, birth, csvEmails, csvEmployeeNos, csvLoginIds);

                if (validation.isValid) {
                    validCount++;
                    if (email != null)
                        csvEmails.add(email);
                    if (employeeNo != null)
                        csvEmployeeNos.add(employeeNo);
                    if (loginId != null)
                        csvLoginIds.add(loginId);
                } else {
                    invalidCount++;
                }

                previewRow = EmployeeCsvPreviewRow.builder()
                        .rowNumber(rowNumber)
                        .name(name)
                        .email(email)
                        .phoneNo(phoneNo)
                        .employeeNo(employeeNo)
                        .loginId(loginId)
                        .password(password)
                        .hireDate(hireDate)
                        .deptName(deptName)
                        .positionName(positionName)
                        .gender(gender)
                        .birth(birth)
                        .isValid(validation.isValid)
                        .failReason(validation.errorMessage)
                        .build();

            } catch (Exception e) {
                // 행 처리 중 예기치 못한 오류 발생 시 해당 행만 실패 처리
                invalidCount++;
                previewRow = EmployeeCsvPreviewRow.builder()
                        .rowNumber(rowNumber)
                        .isValid(false)
                        .failReason("시스템 오류: " + e.toString())
                        .build();
            }

            previewRows.add(previewRow);
        }

        return EmployeeCsvPreviewResponse.builder()
                .totalCount(rows.size())
                .validCount(validCount)
                .invalidCount(invalidCount)
                .rows(previewRows)
                .build();
    }

    /**
     * CSV 행 데이터 유효성 검사
     */
    private ValidationResult validateRow(Long companyId, String name, String email, String phoneNo, String employeeNo,
            String loginId, String password, String hireDate,
            String deptName, String positionName, String gender, String birth, Set<String> csvEmails,
            Set<String> csvEmployeeNos, Set<String> csvLoginIds) {
        List<String> errors = new ArrayList<>();

        // 1. 필수값 체크
        if (isEmpty(name))
            errors.add("이름 누락");
        if (isEmpty(email))
            errors.add("이메일 누락");
        if (isEmpty(phoneNo))
            errors.add("전화번호 누락");
        if (isEmpty(employeeNo))
            errors.add("사번 누락");
        if (isEmpty(loginId))
            errors.add("로그인ID 누락");
        if (isEmpty(password))
            errors.add("비밀번호 누락");
        // hireDate는 선택값으로 변경됨

        // 2. 형식 체크
        if (!isEmpty(email) && !EMAIL_PATTERN.matcher(email).matches()) {
            errors.add("이메일 형식 오류");
        }
        if (!isEmpty(hireDate) && !DATE_PATTERN.matcher(hireDate).matches()) {
            errors.add("입사일 형식 오류(YYYY-MM-DD)");
        }
        if (!isEmpty(birth) && !DATE_PATTERN.matcher(birth).matches()) {
            errors.add("생년월일 형식 오류(YYYY-MM-DD)");
        }
        if (!isEmpty(gender) && !("M".equalsIgnoreCase(gender) || "F".equalsIgnoreCase(gender))) {
            errors.add("성별 형식 오류(M/F)");
        }

        // 3. 중복 체크 (DB & CSV 내부)
        if (!isEmpty(email)) {
            if (csvEmails.contains(email))
                errors.add("파일 내 중복 이메일");
            else if (employeeRepository.existsByEmailAndComIdAndIsDeleted(email, companyId, 'N'))
                errors.add("이미 존재하는 이메일");
        }
        if (!isEmpty(employeeNo)) {
            if (csvEmployeeNos.contains(employeeNo))
                errors.add("파일 내 중복 사번");
            else if (employeeRepository.existsByEmployeeNoAndComIdAndIsDeleted(employeeNo, companyId, 'N'))
                errors.add("이미 존재하는 사번");
        }
        if (!isEmpty(loginId)) {
            if (csvLoginIds.contains(loginId))
                errors.add("파일 내 중복 로그인ID");
            else if (accountRepository.existsByLoginIdAndComIdAndIsDeleted(loginId, companyId, 'N'))
                errors.add("이미 존재하는 로그인ID");
        }

        // 4. 부서/직위 존재 여부 체크
        if (!isEmpty(deptName)
                && !departmentRepository.existsByDeptNameAndComIdAndIsDeleted(deptName, companyId, 'N')) {
            errors.add("존재하지 않는 부서명");
        }
        if (!isEmpty(positionName)
                && !positionRepository.existsByNameAndComIdAndIsDeleted(positionName, companyId, 'N')) {
            errors.add("존재하지 않는 직위명");
        }

        if (errors.isEmpty()) {
            return new ValidationResult(true, null);
        } else {
            return new ValidationResult(false, String.join(", ", errors));
        }
    }

    private String getValue(Map<String, Integer> header, String[] rowData, String keyKeyword) {
        // 헤더 이름이 정확히 일치하지 않을 수 있으므로 포함 여부로 찾음 (예: "이름(필수)" -> "이름")
        Integer index = null;
        for (String headerName : header.keySet()) {
            if (headerName.contains(keyKeyword)) {
                index = header.get(headerName);
                break;
            }
        }

        if (index == null || index >= rowData.length)
            return null;
        String val = rowData[index].trim();
        return val.isEmpty() ? null : val;
    }

    private boolean isEmpty(String str) {
        return str == null || str.isBlank();
    }

    private static class ValidationResult {
        boolean isValid;
        String errorMessage;

        public ValidationResult(boolean isValid, String errorMessage) {
            this.isValid = isValid;
            this.errorMessage = errorMessage;
        }
    }
}
