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
    List<EvaluationQuestion> findAllBySection_EvaluationType_EvalTypeId(Long evalTypeId);

    @Query("""
        select q
        from EvaluationQuestion q
        join q.section s
        join s.evaluationType t
        where t.evalTypeId = :evalTypeId
    """)
    List<EvaluationQuestion> findAllByEvalTypeId(@Param("evalTypeId") Long evalTypeId);

}
