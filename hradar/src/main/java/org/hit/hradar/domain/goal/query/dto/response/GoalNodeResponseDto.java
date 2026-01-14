package org.hit.hradar.domain.goal.query.dto.response;

import org.hit.hradar.domain.goal.command.domain.aggregate.GoalApproveStatus;
import org.hit.hradar.domain.goal.command.domain.aggregate.GoalDepth;
import org.hit.hradar.domain.goal.command.domain.aggregate.GoalType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * Goal 노드
 *  ㄴkpi or okr
 *  ㄴGoal 노드
 *      ㄴkpi or okr
 */

@Getter
@Setter
public class GoalNodeResponseDto {
    private Long goalId;
    private Long parentGoalId;

    private GoalDepth depth;
    private GoalType type;
    private String title;

    private GoalApproveStatus approveStatus;

    // 계산된 현재 진행률 (0~100)
    private BigDecimal progressRate;

    // KPI / OKR 목록 (둘 중 하나만 채워짐)
    private List<KpiListResponseDto> kpis = new ArrayList<>();
    private List<OkrListResponseDto> okrs = new ArrayList<>();

    // 트리
    private List<GoalNodeResponseDto> children = new ArrayList<>();
}
