package org.hit.hradar.domain.goal.query.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OkrDetailResponseDto {
    private Long keyResultId;

    private String content;
    private String metricName;

    private Integer targetValue;
    private Integer currentValue;

    private Integer progressRate;

    /* 성과 로그 */
    private List<OkrProgressLogResponseDto> logs;
}
