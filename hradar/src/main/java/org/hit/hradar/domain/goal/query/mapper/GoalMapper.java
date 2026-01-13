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

    /** 1. 팀에 속한 모든 Goal */
    List<GoalNodeResponseDto> selectGoals(
            @Param("departmentId") Long departmentId
    );

    /** 2. Goal 하위 KPI 목록 */
    List<KpiProgressResponseDto> selectKpis(
            @Param("goalIds") List<Long> goalIds
    );

    /** 3. KPI 누적값 (SUM) */
    List<KpiCurrentSumRow> selectKpiCurrentSums(
            @Param("kpiIds") List<Long> kpiIds
    );

    /** 4. Goal 하위 KR 목록 */
    List<KrProgressResponseDto> selectKrs(
            @Param("goalIds") List<Long> goalIds
    );

    /** 5. KR 최신 진척률 */
    List<KrLatestProgressRow> selectKrLatestProgress(
            @Param("krIds") List<Long> krIds
    );
}
