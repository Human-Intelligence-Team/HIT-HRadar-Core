package org.hit.hradar.domain.evaluation.command.domain.repository;

import org.apache.ibatis.annotations.Param;
import org.hit.hradar.domain.evaluation.command.domain.aggregate.EvaluationQuestion;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EvaluationQuestionRepository {
    EvaluationQuestion save(EvaluationQuestion question);
    Optional<EvaluationQuestion> findById(Long questionId);
    void delete(EvaluationQuestion question);

    @Query("""
        select q
        from EvaluationQuestion q
        join q.section s
        where s.cycleEvaluationType.cycleEvalTypeId = :cycleEvalTypeId
    """)
    List<EvaluationQuestion> findAllByCycleEvalTypeId(
            @Param("cycleEvalTypeId") Long cycleEvalTypeId
    );
}
