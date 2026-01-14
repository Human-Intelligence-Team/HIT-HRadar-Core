package org.hit.hradar.domain.goal.query.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OkrListResponseDto {

    private Long goalId;

    private Long keyResultId;

    private String content;
    private String metricName;

    private Integer targetValue;
    private Integer currentValue;    // 최신 current_progress

    /* 진행률 (0~100) */
    private Integer progressRate;
}
