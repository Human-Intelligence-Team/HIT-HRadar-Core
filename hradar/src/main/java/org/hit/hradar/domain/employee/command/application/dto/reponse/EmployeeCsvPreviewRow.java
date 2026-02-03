package org.hit.hradar.domain.employee.command.application.dto.reponse;

import lombok.Builder;
import lombok.Getter;

/**
 * CSV 미리보기 시 각 행(Row)의 파싱 결과 및 유효성 검증 상태를 담는 DTO
 */
@Getter
@Builder
public class EmployeeCsvPreviewRow {

    private int rowNumber; // CSV 행 번호 (2부터 시작, 1은 헤더)

    private String employeeNo; // 사번
    private String loginId; // 로그인 ID
    private String name; // 이름
    private String password; // 비밀번호 (Preview round-trip용)
    private String email; // 이메일
    private String phoneNo; // 전화번호
    private String hireDate; // 입사일 (YYYY-MM-DD)
    private String deptName; // 부서명
    private String positionName; // 직위명
    private String gender; // 성별(M/F)
    private String birth; // 생년월일(YYYY-MM-DD)

    private boolean isValid; // 유효성 통과 여부
    private String failReason; // 실패 사유 (유효하지 않을 경우)
}
