package org.hit.hradar.domain.evaluation.query.dto.response;

import lombok.Getter;

@Getter
public class DepartmentEvaluationAssignmentDetailResponse {
    /* 평가자 */
    private Long evaluatorId;
    private String evaluatorName;

    /* 평가자 부서 */
    private Long deptId;
    private String deptName;

    /* 피평가자 */
    private Long evaluateeId;
    private String evaluateeName;

    /* 평가 정보 */
    private Long cycleId;
    private String cycleName;

    private Long evalTypeId;
    private String evalTypeName;

    private String assignmentStatus; // PENDING / SUBMITTED
}
