package org.hit.hradar.domain.grading.query.dto.response;
import lombok.Getter;

@Getter
public class EmployeeGradeStatusResponseDto {
    // 사원 정보
    private Long empId;
    private String employeeNo;
    private String employeeName;
    private Long departmentId;

    // 개인 등급 정보
    private Long individualGradeId;
    private Long gradeId;
    private String gradeName;
    private String gradeReason;
    private String gradeStatus;
}
