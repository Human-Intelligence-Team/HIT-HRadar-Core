package org.hit.hradar.domain.evaluation.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.hit.hradar.domain.evaluation.query.dto.response.CycleListResponseDto;

import java.util.List;

@Mapper
public interface CycleMapper {

    //평가 회차 목록 조회
    List<CycleListResponseDto> selectCycleList();
}
