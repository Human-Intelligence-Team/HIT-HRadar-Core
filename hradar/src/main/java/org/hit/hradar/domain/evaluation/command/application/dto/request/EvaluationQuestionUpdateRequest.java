package org.hit.hradar.domain.evaluation.command.application.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hit.hradar.domain.evaluation.command.domain.aggregate.RequiredStatus;

import java.util.List;

@Getter
@NoArgsConstructor
public class EvaluationQuestionUpdateRequest {

    //문항 유형은 수정을 막음

    private String questionContent;

    private RequiredStatus requiredStatus;

    // SCORE일 때만 사용
    private Integer ratingScale;

    // OBJECTIVE일 때만 사용 (전체 교체)
    private List<ObjectiveOptionRequestDto> options;

}
