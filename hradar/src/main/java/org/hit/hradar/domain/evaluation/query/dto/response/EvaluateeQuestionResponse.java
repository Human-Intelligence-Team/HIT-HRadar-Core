package org.hit.hradar.domain.evaluation.query.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class EvaluateeQuestionResponse {
    private Long questionId;
    private String questionType;
    private String questionContent;

    private List<EvaluateeAnswerResponse> responses;
}
