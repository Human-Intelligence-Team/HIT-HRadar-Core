package org.hit.hradar.domain.evaluation.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.hit.hradar.domain.evaluation.query.dto.response.ObjectiveStatRow;
import org.hit.hradar.domain.evaluation.query.dto.response.QuestionMetaRow;
import org.hit.hradar.domain.evaluation.query.dto.response.RatingStatRow;
import org.hit.hradar.domain.evaluation.query.dto.response.SubjectiveAnswerRow;

import java.util.List;

@Mapper
public interface EvaluationResponseMapper {
    List<QuestionMetaRow> selectQuestions(Long evalTypeId);

    List<RatingStatRow> selectRatingStats(Long evalTypeId);

    List<ObjectiveStatRow> selectObjectiveStats(Long evalTypeId);

    List<SubjectiveAnswerRow> selectSubjectiveAnswers(Long evalTypeId);
}
