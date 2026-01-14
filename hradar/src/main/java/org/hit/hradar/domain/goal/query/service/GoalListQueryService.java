package org.hit.hradar.domain.goal.query.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.goal.query.dto.response.*;
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

        // 부서(departmentId)에 속한 Goal 전체를 한 번에 조회
        //    - LEVEL_1/2/3 포함
        List<GoalNodeResponseDto> goals = goalMapper.selectGoals(departmentId);

        //조회된 Goal들의 ID만 뽑아서 KPI/KR 조회에 사용
        List<Long> goalIds = goals.stream()
                .map(GoalNodeResponseDto::getGoalId)
                .toList();

        // ---------------- KPI ----------------

        // goalIds에 속한 KPI 목록을 조회한다.
        List<KpiProgressResponseDto> kpis = goalIds.isEmpty()
                ? List.of()
                : goalMapper.selectKpis(goalIds);

        //누적합 SUM 조회용 kpi id 들
        List<Long> kpiIds = kpis.stream()
                .map(KpiProgressResponseDto::getKpiId)
                .toList();

        // KPI 누적합을 kpiId -> currentValue
        Map<Long, BigDecimal> kpiCurrentMap = kpiIds.isEmpty()
                ? Map.of()
                : goalMapper.selectKpiCurrentSums(kpiIds).stream()
                .collect(Collectors.toMap(
                        KpiCurrentSumRow::getKpiId,
                        row -> row.getCurrentValue() == null ? BigDecimal.ZERO : row.getCurrentValue()
                ));

        //KPI 응답 DTO에 currentValue / progressRate를 계산해서 채운다.
        // (current - start) / (target - start) * 100
        for (KpiProgressResponseDto kpi : kpis) {
            BigDecimal current = kpiCurrentMap.getOrDefault(kpi.getKpiId(), BigDecimal.ZERO);
            kpi.setCurrentValue(current);
            kpi.setProgressRate(calcKpiRate(kpi.getStartValue(), kpi.getTargetValue(), current));
        }

        //계산 완료된 KPI를 goalId 기준으로 묶기
        Map<Long, List<KpiProgressResponseDto>> kpisByGoal =
                kpis.stream().collect(Collectors.groupingBy(KpiProgressResponseDto::getGoalId));

        // ---------------- OKR ----------------

        List<KrProgressResponseDto> krs = goalIds.isEmpty()
                ? List.of()
                : goalMapper.selectKrs(goalIds);

        List<Long> krIds = krs.stream()
                .map(KrProgressResponseDto::getKeyResultId)
                .toList();


        Map<Long, Integer> krLatestMap = krIds.isEmpty()
                ? Map.of()
                : goalMapper.selectKrLatestProgress(krIds).stream()
                .collect(Collectors.toMap(
                        KrLatestProgressRow::getKeyResultId,
                        row -> row.getLatestProgress() == null ? 0 : row.getLatestProgress()
                ));

        // KR 응답 DTO에 최신 진척률(latestProgress) 채우기
        for (KrProgressResponseDto kr : krs) {
            kr.setLatestProgress(krLatestMap.getOrDefault(kr.getKeyResultId(), 0));
        }

        Map<Long, List<KrProgressResponseDto>> krsByGoal =
                krs.stream().collect(Collectors.groupingBy(KrProgressResponseDto::getGoalId));

        // ---------------- Goal에 KPI/OKR 붙이기 ----------------
        for (GoalNodeResponseDto goal : goals) {

            goal.setKpis(kpisByGoal.getOrDefault(goal.getGoalId(), List.of()));

            if (krsByGoal.containsKey(goal.getGoalId())) {
                OkrProgressResponseDto okr = new OkrProgressResponseDto();
                okr.setGoalId(goal.getGoalId());
                okr.setKeyResults(krsByGoal.get(goal.getGoalId()));
                goal.getOkrs().add(okr);
            }
        }

        // ---------------- 트리 구성 ----------------

        //  parentGoalId 기반으로 트리 구성
        List<GoalNodeResponseDto> roots = buildTree(goals);

        // ---------------- 진척률 계산 (네 정책 반영) ----------------
        // - Goal progressRate는 "해당 Goal에 직접 속한 KPI/OKR 평균"만 반영한다.
        // - children(하위 Goal)의 progressRate를 상위에 롤업(평균)하지 않는다.
        for (GoalNodeResponseDto root : roots) {
            calcProgressDfs(root);
        }

        return roots;
    }

    // ---------------- 계산 메서드 ----------------

    // KPI 진척률 계산
    private BigDecimal calcKpiRate(BigDecimal start, BigDecimal target, BigDecimal current) {

        start = start == null ? BigDecimal.ZERO : start;
        target = target == null ? BigDecimal.ZERO : target;
        current = current == null ? BigDecimal.ZERO : current;

        // target-start가 0이면 나눗셈 불가 → 0% 처리
        if (target.subtract(start).compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }

        // (current - start) / (target - start) * 100
        BigDecimal rate = current.subtract(start)
                .divide(target.subtract(start), 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));

        // 0~100 범위
        return rate.max(BigDecimal.ZERO).min(BigDecimal.valueOf(100));
    }

    // 트리를 순회하면서 각 Goal의 자기 KPI/OKR 평균만
    private void calcProgressDfs(GoalNodeResponseDto goal) {

        calcGoalSelfProgress(goal);

        for (GoalNodeResponseDto child : goal.getChildren()) {
            calcProgressDfs(child);
        }
    }

    private void calcGoalSelfProgress(GoalNodeResponseDto goal) {

        List<BigDecimal> candidates = new ArrayList<>();

        // Kpi 일때
        if (!goal.getKpis().isEmpty()) {
            BigDecimal kpiAvg = avg(
                    goal.getKpis().stream()
                            .map(KpiProgressResponseDto::getProgressRate)
                            .filter(Objects::nonNull)
                            .toList()
            );
            candidates.add(kpiAvg);
        }

        // OKR일때
        if (!goal.getOkrs().isEmpty()) {
            OkrProgressResponseDto okr = goal.getOkrs().get(0);

            BigDecimal okrAvg = avg(
                    okr.getKeyResults().stream()
                            .map(kr -> BigDecimal.valueOf(kr.getLatestProgress() == null ? 0 : kr.getLatestProgress()))
                            .toList()
            );

            okr.setProgressRate(okrAvg);
            candidates.add(okrAvg);
        }

        // KPI도 OKR도 없을때
        if (candidates.isEmpty()) {
            goal.setProgressRate(BigDecimal.ZERO);
            return;
        }

        //방어적 처리(KPI OKR 둘 다 있을때)
        goal.setProgressRate(avg(candidates));
    }

    // 평균 계산
    private BigDecimal avg(List<BigDecimal> values) {
        if (values == null || values.isEmpty()) return BigDecimal.ZERO;

        BigDecimal sum = values.stream()
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return sum.divide(BigDecimal.valueOf(values.size()), 2, RoundingMode.HALF_UP);
    }

    // parentGoalId로 트리 구성
    private List<GoalNodeResponseDto> buildTree(List<GoalNodeResponseDto> goals) {

        Map<Long, GoalNodeResponseDto> map = new HashMap<>();
        for (GoalNodeResponseDto g : goals) {
            map.put(g.getGoalId(), g);
        }

        List<GoalNodeResponseDto> roots = new ArrayList<>();

        for (GoalNodeResponseDto g : goals) {
            if (g.getParentGoalId() == null) {
                roots.add(g);
            } else {
                GoalNodeResponseDto parent = map.get(g.getParentGoalId());
                if (parent != null) {
                    parent.getChildren().add(g);
                } else {
                    // parent가 없으면 데이터가 이상하므로 루트로 취급 (장애 방어)
                    roots.add(g);
                }
            }
        }

        return roots;
    }
}
