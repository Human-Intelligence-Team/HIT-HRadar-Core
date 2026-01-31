package org.hit.hradar.domain.evaluation.command.application.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EvaluationResponseItemRequest {

    @NotNull
    private Long questionId;

    /**
     * OBJECTIVE / SUBJECTIVE / RATING
     */
    @NotNull
    private String responseType;

    /**
     * RATING일 때 사용
     */
    private Integer score;

    /**
     * SUBJECTIVE일 때 사용
     */
    private String textAnswer;

    /**
     * OBJECTIVE일 때 사용
     */
    private Long optionId;
}
