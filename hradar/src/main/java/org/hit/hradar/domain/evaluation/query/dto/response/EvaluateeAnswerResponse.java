package org.hit.hradar.domain.evaluation.query.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EvaluateeAnswerResponse {

    private Integer score;
    private String textAnswer;
    private String optionContent;

}
