package org.hit.hradar.domain.goal.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.hit.hradar.domain.goal.query.dto.response.OkrListResponseDto;

import java.util.List;

@Mapper
public interface OkrListMapper {

    List<OkrListResponseDto> findOkrsByGoalId(
            @Param("goalId") Long goalId
    );
}
