package org.hit.hradar.domain.evaluation.query.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class RejectionCountChartResponse {

    private List<String> labels;   // YYYY-MM
    private List<Long> values;     // 월별 반려 횟수
}
