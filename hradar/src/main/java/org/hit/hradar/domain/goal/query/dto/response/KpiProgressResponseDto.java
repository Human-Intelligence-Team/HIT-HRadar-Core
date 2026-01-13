package org.hit.hradar.domain.goal.query.dto.response;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KpiProgressResponseDto {

    private Long kpiId;
    private Long goalId;

    private String metricName;

    private BigDecimal startValue;
    private BigDecimal targetValue;

    // 계산된 값
    private BigDecimal currentValue;
    private BigDecimal progressRate;
}
