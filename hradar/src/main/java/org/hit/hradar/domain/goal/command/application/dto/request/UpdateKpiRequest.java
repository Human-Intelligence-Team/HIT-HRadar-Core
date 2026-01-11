package org.hit.hradar.domain.goal.command.application.dto.request;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class UpdateKpiRequest {
    private String metricName;
    private BigDecimal startValue;
    private BigDecimal targetValue;
    private Long actorId;
}
