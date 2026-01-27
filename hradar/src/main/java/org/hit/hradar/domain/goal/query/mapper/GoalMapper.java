package org.hit.hradar.domain.goal.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.hit.hradar.domain.goal.query.dto.response.*;

import java.util.List;

/**
 * 바 그래프 전용 Query Mapper
 * - "현재 상태"만 조회
 */
@Mapper
public interface GoalMapper {

    /* 1. Goal 기본 목록 (트리용) */
    List<GoalNodeResponseDto> selectGoals(
            @Param("departmentId") Long departmentId
    );

    /* 2. Goal 하위 KPI 목록 (STEP2 DTO) */
    List<KpiListResponseDto> selectKpisByGoalIds(
            @Param("goalIds") List<Long> goalIds
    );

    /* 3. Goal 하위 OKR 목록 (STEP4 DTO) */
    List<OkrListResponseDto> selectOkrsByGoalIds(
            @Param("goalIds") List<Long> goalIds
    );

    List<GoalNodeResponseDto> selectMyGoals(
            @Param("ownerId") Long ownerId
    );}

