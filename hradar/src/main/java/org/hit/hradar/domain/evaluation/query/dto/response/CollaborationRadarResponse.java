package org.hit.hradar.domain.evaluation.query.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * [협업 레이더 차트 응답]
 *
 * labels: 레이더 축 이름들(문항 내용)
 * values: 각 축에 대응하는 정규화 점수(0~100)
 * max: 레이더 차트 indicator.max (기본 100)
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CollaborationRadarResponse {
    private List<String> labels;
    private List<Double> values;
    private int max;
}
