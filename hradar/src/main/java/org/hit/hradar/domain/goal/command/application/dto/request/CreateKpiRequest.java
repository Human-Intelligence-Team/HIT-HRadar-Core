package org.hit.hradar.domain.goal.command.application.dto.request;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class CreateKpiRequest {

    private Long goalId; // KPI가 속할 Goal
    private String metricName;
    private BigDecimal startValue;
    private BigDecimal targetValue;
}
