package org.hit.hradar.domain.evaluation.query.dto.response.row;

import lombok.Getter;
import org.hit.hradar.domain.evaluation.command.domain.aggregate.QuestionType;
import org.hit.hradar.domain.evaluation.command.domain.aggregate.RequiredStatus;

@Getter
public class EvaluationQuestionRow {

    private Long questionId;
    private Long sectionId;

    private QuestionType questionType;
    private String questionContent;
    private RequiredStatus requiredStatus;

    // RATING 전용
    private Integer ratingScale;
}
