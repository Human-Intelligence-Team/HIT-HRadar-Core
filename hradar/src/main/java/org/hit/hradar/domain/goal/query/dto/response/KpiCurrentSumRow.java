package org.hit.hradar.domain.goal.query.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * KPI 누적합 Row
 */
@Getter
@Setter
public class KpiCurrentSumRow {
    private Long kpiId;
    private BigDecimal currentValue;
}
