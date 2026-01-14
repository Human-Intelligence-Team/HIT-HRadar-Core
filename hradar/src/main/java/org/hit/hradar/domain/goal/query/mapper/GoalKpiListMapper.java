package org.hit.hradar.domain.goal.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.hit.hradar.domain.goal.query.dto.response.GoalKpiListResponseDto;

import java.util.List;

@Mapper
public interface GoalKpiListMapper {

    List<GoalKpiListResponseDto> findKpisByGoalId(
            @Param("goalId") Long goalId
    );
}
