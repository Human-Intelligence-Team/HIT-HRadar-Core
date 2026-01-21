package org.hit.hradar.domain.grading.query.dto.response;

import lombok.Getter;

@Getter
public class DeptGradeStatusResponseDto {
    private Long departmentId;
    private String departmentName;

    private Long deptGradeId;
    private Long gradeId;
    private String gradeName;
    private String gradeReason;
    private String gradeStatus;
}
