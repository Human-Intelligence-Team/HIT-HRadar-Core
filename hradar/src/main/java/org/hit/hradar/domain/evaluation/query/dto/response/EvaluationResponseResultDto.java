package org.hit.hradar.domain.evaluation.query.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hit.hradar.domain.evaluation.command.domain.aggregate.QuestionType;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class EvaluationResponseResultDto {
    private Long questionId;
    private String questionContent;
    private QuestionType questionType;

    // 요약
    private Double averageScore;                 // RATING
    private String mostSelectedOption;           // OBJECTIVE

    // 분포
    private List<ScoreDistributionDto> scoreDistributions;
    private List<OptionDistributionDto> optionDistributions;

    // 주관식
    private List<String> subjectiveAnswers;

}
