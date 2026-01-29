package org.hit.hradar.domain.evaluation.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.hit.hradar.domain.evaluation.query.dto.response.CycleEvaluationTypeResponse;

import java.util.List;

@Mapper
public interface CycleEvaluationTypeMapper {
    List<CycleEvaluationTypeResponse> selectEvaluationTypesByCycleId(
            @Param("cycleId") Long cycleId
    );

    Long findCycleEvalTypeId(
            @Param("cycleId") Long cycleId,
            @Param("evalTypeId") Long evalTypeId
    );
}
