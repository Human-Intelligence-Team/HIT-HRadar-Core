package org.hit.hradar.domain.evaluation.query.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hit.hradar.domain.evaluation.command.domain.aggregate.QuestionType;
import org.hit.hradar.domain.evaluation.command.domain.aggregate.RequiredStatus;

import java.util.List;

@Getter
@AllArgsConstructor
public class EvaluationQuestionResponseDto {

    private Long questionId;
    private QuestionType questionType;
    private String questionContent;
    private RequiredStatus requiredStatus;

    //RATING 전용
    private Integer ratingScale;

    //OBJECTIVE 전용
    private List<ObjectiveOptionResponseDto> options;
}
