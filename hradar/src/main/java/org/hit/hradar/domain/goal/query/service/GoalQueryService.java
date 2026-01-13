package org.hit.hradar.domain.goal.query.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.goal.query.common.calculator.GoalProgressCalculator;
import org.hit.hradar.domain.goal.query.common.calculator.KpiProgressCalculator;
import org.hit.hradar.domain.goal.query.dto.response.*;
import org.hit.hradar.domain.goal.query.mapper.GoalMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GoalQueryService {

    private final GoalMapper goalMapper;
    private final KpiProgressCalculator kpiCalculator;
    private final GoalProgressCalculator goalCalculator;

    public List<GoalNodeResponseDto> getGoalTree(Long departmentId) {

        List<GoalNodeResponseDto> goals = goalMapper.selectGoals(departmentId);
        List<Long> goalIds = goals.stream().map(GoalNodeResponseDto::getGoalId).toList();

        List<KpiProgressResponseDto> kpis =
                goalIds.isEmpty() ? List.of() : goalMapper.selectKpis(goalIds);

        Map<Long, BigDecimal> currentMap = goalMapper
                .selectKpiCurrentSums(
                        kpis.stream().map(KpiProgressResponseDto::getKpiId).toList()
                )
                .stream()
                .collect(Collectors.toMap(
                        KpiCurrentSumRow::getKpiId,
                        r -> Optional.ofNullable(r.getCurrentValue()).orElse(BigDecimal.ZERO)
                ));

        for (KpiProgressResponseDto kpi : kpis) {
            BigDecimal current = currentMap.getOrDefault(kpi.getKpiId(), BigDecimal.ZERO);
            kpi.setCurrentValue(current);
            kpi.setProgressRate(
                    kpiCalculator.calcRate(
                            kpi.getStartValue(),
                            kpi.getTargetValue(),
                            current
                    )
            );
        }

        Map<Long, List<KpiProgressResponseDto>> kpisByGoal =
                kpis.stream().collect(Collectors.groupingBy(KpiProgressResponseDto::getGoalId));

        for (GoalNodeResponseDto goal : goals) {
            goal.setKpis(kpisByGoal.getOrDefault(goal.getGoalId(), List.of()));
        }

        List<GoalNodeResponseDto> roots = buildTree(goals);

        for (GoalNodeResponseDto root : roots) {
            calcDfs(root);
        }

        return roots;
    }

    private void calcDfs(GoalNodeResponseDto goal) {
        goal.setProgressRate(goalCalculator.calcSelfProgress(goal));
        goal.getChildren().forEach(this::calcDfs);
    }

    private List<GoalNodeResponseDto> buildTree(List<GoalNodeResponseDto> goals) {
        Map<Long, GoalNodeResponseDto> map = new HashMap<>();
        for (GoalNodeResponseDto g : goals) map.put(g.getGoalId(), g);

        List<GoalNodeResponseDto> roots = new ArrayList<>();

        for (GoalNodeResponseDto g : goals) {
            if (g.getParentGoalId() == null) {
                roots.add(g);
            } else {
                GoalNodeResponseDto parent = map.get(g.getParentGoalId());
                if (parent != null) {
                    parent.getChildren().add(g);
                } else {
                    // parent가 없으면 루트로 처리
                    roots.add(g);
                }
            }
        }

        return roots;
    }
}
