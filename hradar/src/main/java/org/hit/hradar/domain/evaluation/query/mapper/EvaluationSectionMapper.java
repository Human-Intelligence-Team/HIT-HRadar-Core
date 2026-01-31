package org.hit.hradar.domain.evaluation.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.hit.hradar.domain.evaluation.query.dto.response.row.EvaluationSectionRow;

import java.util.List;


@Mapper
public interface EvaluationSectionMapper {
    List<EvaluationSectionRow> findByCycleEvalTypeId(
            @Param("cycleEvalTypeId") Long cycleEvalTypeId
    );
}
