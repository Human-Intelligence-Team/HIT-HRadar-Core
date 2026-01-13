package org.hit.hradar.domain.goal.query.common.calculator;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/*
* OKR의 progressRate = KR들의 latestProgress 평균
* */
@Component
public class OkrProgressCalculator {

    public BigDecimal calcOkrRate(List<Integer> progresses) {
        if (progresses == null || progresses.isEmpty()) {
            return BigDecimal.ZERO;
        }

        //progresses 합계
        BigDecimal sum = progresses.stream()
                .map(p -> BigDecimal.valueOf(p == null ? 0 : p))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 평균 = 합계 / 개수
        return sum.divide(
                BigDecimal.valueOf(progresses.size()),
                2,
                RoundingMode.HALF_UP
        );
    }
}

