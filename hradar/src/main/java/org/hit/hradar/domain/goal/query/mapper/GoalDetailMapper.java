package org.hit.hradar.domain.goal.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.hit.hradar.domain.goal.query.dto.response.GoalDetailResponseDto;

@Mapper
public interface GoalDetailMapper {

    GoalDetailResponseDto findGoalDetail(@Param("goalId") Long goalId);
}
