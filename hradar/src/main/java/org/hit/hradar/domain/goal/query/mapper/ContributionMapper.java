package org.hit.hradar.domain.goal.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.hit.hradar.domain.goal.query.dto.response.row.ContributionByTeamGoalRow;

import java.util.List;

@Mapper
public interface ContributionMapper {

    /**
     * 팀 목표(Level1)별 내 기여도 조회
     *
     * 기간은 팀 목표의 start_date ~ end_date를 사용
     */
    List<ContributionByTeamGoalRow> selectMyContributionByTeamGoals(
            @Param("empId") Long empId
    );

}
