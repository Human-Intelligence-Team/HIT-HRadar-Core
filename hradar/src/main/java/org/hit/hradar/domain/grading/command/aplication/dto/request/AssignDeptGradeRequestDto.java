package org.hit.hradar.domain.grading.command.aplication.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AssignDeptGradeRequestDto {

    private Long companyId;
    private Long cycleId;
    private Long departmentId;

    private Long gradeId;
    private String gradeReason;
}
