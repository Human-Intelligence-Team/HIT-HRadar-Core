package org.hit.hradar.domain.evaluation.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.hit.hradar.domain.evaluation.query.dto.response.EvaluationSectionResponseDto;

import java.util.List;

@Mapper
public interface EvaluationSectionMapper {

    List<EvaluationSectionResponseDto> selectSectionsByEvalTypeId(
            @Param("evalTypeId") Long evalTypeId
    );
}
