package org.hit.hradar.domain.evaluation.query.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hit.hradar.domain.evaluation.command.domain.aggregate.QuestionType;
import org.hit.hradar.domain.evaluation.command.domain.aggregate.RequiredStatus;

import java.util.List;

@Getter
@AllArgsConstructor
public class EvaluationQuestionResponse {

    private Long questionId;
    private QuestionType questionType;
    private String questionContent;
    private RequiredStatus requiredStatus;

    // RATING 전용 (아니면 null)
    private Integer ratingScale;

    // OBJECTIVE 전용 (아니면 빈 리스트 or null)
    private List<ObjectiveOptionResponse> options;
}