package org.hit.hradar.domain.evaluation.query.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class EvaluationAssignmentResponse {
    private Long assignmentId;

    // 회차 정보
    private Long cycleId;
    private String cycleName;
    private String year;
    private String quarter;

    // 평가 유형 정보
    private Long evalTypeId;
    private String evalTypeName;

    // 평가 관계
    private Long evaluatorId;
    private Long evaluateeId;

    // 상태
    private String assignmentStatus;
    private LocalDateTime submittedAt;
}
