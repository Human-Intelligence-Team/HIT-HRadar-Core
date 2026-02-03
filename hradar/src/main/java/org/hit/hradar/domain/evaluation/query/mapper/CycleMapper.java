package org.hit.hradar.domain.evaluation.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.hit.hradar.domain.evaluation.query.dto.response.CycleDetailResponseDto;
import org.hit.hradar.domain.evaluation.query.dto.response.CycleListResponseDto;

import java.util.List;

@Mapper
public interface CycleMapper {

    //평가 회차 목록 조회
    List<CycleListResponseDto> selectCycleList();

    //회차 단건 조회
    CycleDetailResponseDto selectCycleDetail(
            @Param("cycleId") Long cycleId
    );

    // 회차에 포함된 평가 유형 조회
    List<String> selectEvaluationTypesByCycleId(
            @Param("cycleId") Long cycleId
    );
}
