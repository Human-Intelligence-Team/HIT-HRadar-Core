package org.hit.hradar.domain.grading.command.aplication.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AssignIndividualGradeRequestDto {
    private Long companyId;
    private Long cycleId;
    private Long empId;

    private Long gradeId;
    private String gradeReason;
}
