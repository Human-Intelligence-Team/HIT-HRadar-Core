package org.hit.hradar.domain.evaluation.query.dto.response.row;

import lombok.Getter;

@Getter
public class JobSatisfactionRow {
    private Long questionId;
    private String label;
    private Double value; // 0~100, 소수 1자리
}