package org.hit.hradar.domain.goal.query.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class GoalKpiListResponseDto {

    private Long kpiId;

    //Kpi 정보
    private String metricName;
    private BigDecimal startValue;
    private BigDecimal targetValue;

    //계산된 값
    private BigDecimal currentValue; //start + SUM(log_value)
    private BigDecimal progressRate;
}
