package org.hit.hradar.domain.goal.query.common.calculator;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.goal.query.dto.response.GoalNodeResponseDto;
import org.hit.hradar.domain.goal.query.dto.response.KpiProgressResponseDto;
import org.hit.hradar.domain.goal.query.dto.response.KrProgressResponseDto;
import org.hit.hradar.domain.goal.query.dto.response.OkrProgressResponseDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Goal progressRate는 "해당 Goal에 직접 속한 KPI/OKR 평균"만 반영
 * children(하위 Goal)의 progressRate는 상위로 롤업하지 않는다.
 *
 * - GoalNodeResponseDto 하나를 받아서,
 *   그 Goal이 가지고 있는 KPI/OKR 기반으로 progressRate를 계산한다.
 */

@Component
@RequiredArgsConstructor
public class GoalProgressCalculator {

    private final OkrProgressCalculator okrCalculator;

    public BigDecimal calcSelfProgress(GoalNodeResponseDto goal) {

        List<BigDecimal> candidates = new ArrayList<>();

        if (!goal.getKpis().isEmpty()) {
            candidates.add(avg(
                    goal.getKpis().stream()
                            .map(KpiProgressResponseDto::getProgressRate)
                            .toList()
            ));
        }

        if (!goal.getOkrs().isEmpty()) {
            OkrProgressResponseDto okr = goal.getOkrs().get(0);

            BigDecimal okrRate = okrCalculator.calcOkrRate(
                    okr.getKeyResults().stream()
                            .map(KrProgressResponseDto::getLatestProgress)
                            .toList()
            );

            okr.setProgressRate(okrRate);
            candidates.add(okrRate);
        }

        return candidates.isEmpty() ? BigDecimal.ZERO : avg(candidates);
    }

    private BigDecimal avg(List<BigDecimal> values) {
        BigDecimal sum = values.stream()
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return sum.divide(
                BigDecimal.valueOf(values.size()),
                2,
                RoundingMode.HALF_UP
        );
    }
}
