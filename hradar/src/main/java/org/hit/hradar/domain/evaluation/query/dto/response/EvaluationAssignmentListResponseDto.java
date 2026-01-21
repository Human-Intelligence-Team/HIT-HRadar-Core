package org.hit.hradar.domain.evaluation.query.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class EvaluationAssignmentListResponseDto {

    private Long assignmentId;

    // 회차
    private Long cycleId;
    private String cycleName;

    // 평가 유형
    private String evalTypeCode;

    // 평가자 / 피평가자
    private Long evaluatorId;
    private Long evaluateeId;

    // 상태
    private String assignmentStatus;

    // 제출 시각
    private LocalDateTime submittedAt;

}
