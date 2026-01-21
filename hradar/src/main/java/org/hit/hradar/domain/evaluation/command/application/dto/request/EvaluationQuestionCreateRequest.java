package org.hit.hradar.domain.evaluation.command.application.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hit.hradar.domain.evaluation.command.domain.aggregate.QuestionType;
import org.hit.hradar.domain.evaluation.command.domain.aggregate.RequiredStatus;

import java.util.List;

@Getter
@NoArgsConstructor
public class EvaluationQuestionCreateRequest {

    private QuestionType questionType;
    private String questionContent;
    private RequiredStatus requiredStatus;

    private Integer ratingScale; //RATING일 때만 사용
    private List<ObjectiveOptionRequestDto> options;
}
