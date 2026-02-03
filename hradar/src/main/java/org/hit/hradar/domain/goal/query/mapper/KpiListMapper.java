package org.hit.hradar.domain.goal.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.hit.hradar.domain.goal.query.dto.response.KpiListResponseDto;

import java.util.List;

@Mapper
public interface KpiListMapper {

    List<KpiListResponseDto> findKpisByGoalId(
            @Param("goalId") Long goalId
    );
}
