package org.hit.hradar.domain.grading.query.dto.response;

import lombok.Getter;

@Getter
public class GradeObjectionAdminResponseDto {
    private Long objectionId;

    private Long individualGradeId;

    private Long employeeId;
    private String employeeName;
    private String employeeNo;

    private Long departmentId;
    private String departmentName;

    private String objectionReason;
    private String objectionStatus;
    private String objectionResult;

}
