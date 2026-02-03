package org.hit.hradar.domain.goal.query.service;


import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.goal.command.domain.aggregate.GoalType;
import org.hit.hradar.domain.goal.query.dto.response.GoalNodeResponseDto;
import org.hit.hradar.domain.goal.query.dto.response.KpiListResponseDto;
import org.hit.hradar.domain.goal.query.dto.response.OkrListResponseDto;
import org.hit.hradar.domain.goal.query.mapper.GoalMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GoalListQueryService {

    private final GoalMapper goalMapper;

    public List<GoalNodeResponseDto> getGoalTree(Long departmentId) {

        /* =========================
           1. Goal 기본 목록 조회
           ========================= */
        List<GoalNodeResponseDto> goals = goalMapper.selectGoals(departmentId);

        if (goals.isEmpty()) {
            return List.of();
        }

        List<Long> goalIds = goals.stream()
                .map(GoalNodeResponseDto::getGoalId)
                .toList();

        /* =========================
           2. KPI 목록 조회 (STEP2 DTO 재사용)
           ========================= */
        List<KpiListResponseDto> kpis =
                goalMapper.selectKpisByGoalIds(goalIds);

        Map<Long, List<KpiListResponseDto>> kpisByGoal =
                kpis.stream().collect(Collectors.groupingBy(
                        KpiListResponseDto::getGoalId
                ));

        /* =========================
           3. OKR 목록 조회 (STEP4 DTO 재사용)
           ========================= */
        List<OkrListResponseDto> okrs =
                goalMapper.selectOkrsByGoalIds(goalIds);

        Map<Long, List<OkrListResponseDto>> okrsByGoal =
                okrs.stream().collect(Collectors.groupingBy(
                        OkrListResponseDto::getGoalId
                ));

        /* =========================
           4. Goal에 KPI / OKR 붙이기
           ========================= */
        for (GoalNodeResponseDto goal : goals) {

            // KPI Goal
            if (goal.getType() == GoalType.KPI) {
                goal.setKpis(
                        kpisByGoal.getOrDefault(goal.getGoalId(), List.of())
                );
            }

            if (goal.getType() == GoalType.OKR) {
                goal.setOkrs(
                        okrsByGoal.getOrDefault(goal.getGoalId(), List.of())
                );
            }

        }

        /* =========================
           5. 트리 구성
           ========================= */
        List<GoalNodeResponseDto> roots = buildTree(goals);

        /* =========================
           6. Goal progress 계산
           (직속 KPI/OKR 평균만)
           ========================= */
        for (GoalNodeResponseDto root : roots) {
            calcProgressDfs(root);
        }

        return roots;
    }

    /* =========================
       Goal Progress 계산
       ========================= */

    private void calcProgressDfs(GoalNodeResponseDto goal) {

        List<BigDecimal> rates = new ArrayList<>();

        if (!goal.getKpis().isEmpty()) {
            rates.add(avg(
                    goal.getKpis().stream()
                            .map(KpiListResponseDto::getProgressRate)
                            .toList()
            ));
        }

        if (!goal.getOkrs().isEmpty()) {
            rates.add(avg(
                    goal.getOkrs().stream()
                            .map(o -> BigDecimal.valueOf(o.getProgressRate()))
                            .toList()
            ));
        }

        goal.setProgressRate(
                rates.isEmpty() ? BigDecimal.ZERO : avg(rates)
        );

        for (GoalNodeResponseDto child : goal.getChildren()) {
            calcProgressDfs(child);
        }
    }

    private BigDecimal avg(List<BigDecimal> values) {
        if (values == null || values.isEmpty()) return BigDecimal.ZERO;

        BigDecimal sum = values.stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return sum.divide(
                BigDecimal.valueOf(values.size()),
                2,
                RoundingMode.HALF_UP
        );
    }

    private List<GoalNodeResponseDto> buildTree(List<GoalNodeResponseDto> goals) {

        Map<Long, GoalNodeResponseDto> map = new HashMap<>();
        goals.forEach(g -> map.put(g.getGoalId(), g));

        List<GoalNodeResponseDto> roots = new ArrayList<>();

        for (GoalNodeResponseDto g : goals) {
            if (g.getParentGoalId() == null) {
                roots.add(g);
            } else {
                map.get(g.getParentGoalId())
                        .getChildren()
                        .add(g);
            }
        }

        return roots;
    }

    public List<GoalNodeResponseDto> getMyGoal(Long empId) {

    /* =========================
       1. 내 Goal 목록 조회
       ========================= */
        List<GoalNodeResponseDto> goals =
                goalMapper.selectMyGoals(empId);

        if (goals.isEmpty()) {
            return List.of();
        }

        List<Long> goalIds = goals.stream()
                .map(GoalNodeResponseDto::getGoalId)
                .toList();

    /* =========================
       2. KPI 목록 조회
       ========================= */
        List<KpiListResponseDto> kpis =
                goalMapper.selectKpisByGoalIds(goalIds);

        Map<Long, List<KpiListResponseDto>> kpisByGoal =
                kpis.stream().collect(Collectors.groupingBy(
                        KpiListResponseDto::getGoalId
                ));

    /* =========================
       3. OKR 목록 조회
       ========================= */
        List<OkrListResponseDto> okrs =
                goalMapper.selectOkrsByGoalIds(goalIds);

        Map<Long, List<OkrListResponseDto>> okrsByGoal =
                okrs.stream().collect(Collectors.groupingBy(
                        OkrListResponseDto::getGoalId
                ));

    /* =========================
       4. KPI / OKR 붙이기
       ========================= */
        for (GoalNodeResponseDto goal : goals) {

            if (goal.getType() == GoalType.KPI) {
                goal.setKpis(
                        kpisByGoal.getOrDefault(goal.getGoalId(), List.of())
                );
            }

            if (goal.getType() == GoalType.OKR) {
                goal.setOkrs(
                        okrsByGoal.getOrDefault(goal.getGoalId(), List.of())
                );
            }
        }

    /* =========================
       5. 트리 구성
       ========================= */
        List<GoalNodeResponseDto> roots = buildTree(goals);

    /* =========================
       6. progress 계산
       ========================= */
        for (GoalNodeResponseDto root : roots) {
            calcProgressDfs(root);
        }

        return roots;
    }
}
