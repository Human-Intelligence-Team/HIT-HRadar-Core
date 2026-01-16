package org.hit.hradar.domain.goal.query.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class KpiProgressLogResponseDto {

    private LocalDate logDate;

    private Integer inputValue;
    private BigDecimal cumulativeValue; //누적값
    private BigDecimal progressRate;
}
