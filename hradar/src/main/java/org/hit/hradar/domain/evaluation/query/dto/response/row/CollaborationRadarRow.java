package org.hit.hradar.domain.evaluation.query.dto.response.row;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * [협업 레이더 차트 - 문항별 집계 Row]
 * - 문항 하나 = 레이더 차트 축 하나(선택지 A)
 *
 * label: 레이더 축 이름 (question_content)
 * value: 정규화 점수(0~100), 소수 1자리
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CollaborationRadarRow {

    private Long questionId;
    private String label;
    private Double value;
}
