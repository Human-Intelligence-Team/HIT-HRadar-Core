package org.hit.hradar.domain.evaluation.query.dto.response;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EvaluationResponseQueryDto {

    private Long assignmentId;
    private String assignmentStatus;

    private Long questionId;
    private String questionType;
    private String requiredStatus;

    private Integer score;
    private String textAnswer;
    private Long optionId;
}
