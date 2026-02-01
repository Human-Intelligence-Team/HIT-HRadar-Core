package org.hit.hradar.domain.goal.query.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * [업무 기여도 차트 응답 DTO]
 *
 * 프론트(ECharts)에서 바로 쓰기 좋은 구조
 * - categories : 팀 목표명 (y축)
 * - values     : 내 기여도(%) 값
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ContributionBarChartResponse {

    private List<String> categories;
    private List<Double> values;
}
