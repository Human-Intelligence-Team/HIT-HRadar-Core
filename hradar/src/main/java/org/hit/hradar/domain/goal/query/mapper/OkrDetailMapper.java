package org.hit.hradar.domain.goal.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.hit.hradar.domain.goal.query.dto.response.OkrDetailResponseDto;
import org.hit.hradar.domain.goal.query.dto.response.OkrProgressLogResponseDto;

import java.util.List;

@Mapper
public interface OkrDetailMapper {

    OkrDetailResponseDto findKrSummary(@Param("keyResultId") Long keyResultId);

    List<OkrProgressLogResponseDto> findKrLogs(@Param("keyResultId") Long keyResultId);
}
