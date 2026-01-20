package org.hit.hradar.domain.evaluation.command.application.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EvaluationResponseSaveRequest {
    private Long questionId;

    // OBJECTIVE
    private Long optionId;

    // RATING
    private Integer score;

    // SUBJECTIVE
    private String text;
}
