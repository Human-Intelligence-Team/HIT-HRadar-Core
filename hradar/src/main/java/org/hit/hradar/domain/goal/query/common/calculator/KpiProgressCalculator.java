package org.hit.hradar.domain.goal.query.common.calculator;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

/*
 * start/target/current 값을 받아 KPI 진행률(0~100)을 계산
 *  (current - start) / (target - start) * 100
* */
@Component
public class KpiProgressCalculator {

    public BigDecimal calcRate(
            BigDecimal start,
            BigDecimal target,
            BigDecimal current
    ) {
        //null방어
        start = defaultZero(start);
        target = defaultZero(target);
        current = defaultZero(current);

        //목표/시작이 같으면 0%로 처리
        if (target.subtract(start).compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        // (current - start) / (target - start) * 1
        BigDecimal rate = current.subtract(start)
                .divide(target.subtract(start), 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));

        //0~100으로 제한
        return rate.max(BigDecimal.ZERO).min(BigDecimal.valueOf(100));
    }

    //null 방어
    private BigDecimal defaultZero(BigDecimal v) {
        return v == null ? BigDecimal.ZERO : v;
    }
}
