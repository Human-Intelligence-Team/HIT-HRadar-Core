package org.hit.hradar.domain.grading.command.aplication.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class IndividualGradeObjectionApproveRequestDto {

    private Long gradeId;
    private String gradeReason;
}
