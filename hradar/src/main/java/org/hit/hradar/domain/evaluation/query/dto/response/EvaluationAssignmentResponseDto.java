package org.hit.hradar.domain.evaluation.query.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EvaluationAssignmentResponseDto {

    private Long assignmentId;

    //평가 유형
    private Long evalTypeId;
    private String evalTypeCode;

    //피평가자
    private Long evaluateeId;

    //상태
    private String assignmentStatus;
}
