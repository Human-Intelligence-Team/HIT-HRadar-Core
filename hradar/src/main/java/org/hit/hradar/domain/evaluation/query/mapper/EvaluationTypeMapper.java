package org.hit.hradar.domain.evaluation.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.hit.hradar.domain.evaluation.query.dto.response.EvaluationTypeResponseDto;

import java.util.List;

@Mapper
public interface EvaluationTypeMapper {
    List<EvaluationTypeResponseDto> selectEvaluationTypesByCycle(
            @Param("cycleId") Long cycleId
    );
}
