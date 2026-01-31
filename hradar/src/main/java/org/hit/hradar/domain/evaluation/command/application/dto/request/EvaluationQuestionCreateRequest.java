package org.hit.hradar.domain.evaluation.command.application.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hit.hradar.domain.evaluation.command.domain.aggregate.QuestionType;
import org.hit.hradar.domain.evaluation.command.domain.aggregate.RequiredStatus;

import java.util.List;

@Getter
@NoArgsConstructor
@Setter
public class EvaluationQuestionCreateRequest {

    private Long sectionId;
    private QuestionType questionType;
    private String questionContent;
    private Integer ratingScale;
    private RequiredStatus requiredStatus;
    private List<ObjectiveOptionRequestDto> options;
}
