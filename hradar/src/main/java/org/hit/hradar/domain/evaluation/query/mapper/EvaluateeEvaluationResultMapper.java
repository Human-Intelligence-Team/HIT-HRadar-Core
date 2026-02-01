package org.hit.hradar.domain.evaluation.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.hit.hradar.domain.evaluation.query.dto.response.row.EvaluateeResponseRow;

import java.util.List;

@Mapper
public interface EvaluateeEvaluationResultMapper {

    List<EvaluateeResponseRow> selectEvaluateeResponses(
            @Param("evaluateeId") Long evaluateeId,
            @Param("cycleId") Long cycleId,
            @Param("evalTypeId") Long evalTypeId
    );
}