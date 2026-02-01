package org.hit.hradar.domain.evaluation.query.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;


@Getter
@Builder
@AllArgsConstructor
public class JobSatisfactionChartResponse {

    private List<String> labels;
    private List<Double> barValues;
    private Gauge gauge;

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Gauge {
        private Double average;   // 예: 78.3
        private Integer percentage; // 예: 78
    }
}
