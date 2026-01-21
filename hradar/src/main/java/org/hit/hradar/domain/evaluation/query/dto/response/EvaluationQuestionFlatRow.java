package org.hit.hradar.domain.evaluation.query.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hit.hradar.domain.evaluation.command.domain.aggregate.QuestionType;
import org.hit.hradar.domain.evaluation.command.domain.aggregate.RequiredStatus;

@Getter
@AllArgsConstructor
public class EvaluationQuestionFlatRow {

    private Long questionId;
    private QuestionType questionType;
    private String questionContent;
    private RequiredStatus requiredStatus;

    private Integer ratingScale;

    // OBJECTIVE option
    private Long optionId;
    private String optionContent;
    private Integer optionScore;
}
