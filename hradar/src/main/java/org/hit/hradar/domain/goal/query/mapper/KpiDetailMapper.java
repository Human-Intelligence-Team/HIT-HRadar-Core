package org.hit.hradar.domain.goal.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.hit.hradar.domain.goal.query.dto.response.KpiDetailResponseDto;
import org.hit.hradar.domain.goal.query.dto.response.KpiProgressLogResponseDto;

import java.util.List;

@Mapper
public interface KpiDetailMapper {

    KpiDetailResponseDto findKpiSummary(@Param("kpiId") Long kpiId);

    List<KpiProgressLogResponseDto> findKpiLogs(@Param("kpiId") Long kpiId);
}
