package org.hit.hradar.domain.goal.query.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class KpiDetailResponseDto {

    private Long kpiId;

    private String metricName;

    private BigDecimal startValue;
    private BigDecimal targetValue;

    private BigDecimal currentValue;
    private BigDecimal progressRate;

    private List<KpiProgressLogResponseDto> logs;
}
