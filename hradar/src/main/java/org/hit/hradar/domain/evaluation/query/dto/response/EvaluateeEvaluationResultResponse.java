package org.hit.hradar.domain.evaluation.query.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class EvaluateeEvaluationResultResponse {

    private Long cycleId;
    private String cycleName;

    private Long evalTypeId;
    private String evalTypeName;

    private List<EvaluateeQuestionResponse> questions;
}
