package org.hit.hradar.domain.evaluation.query.dto.response;

import lombok.Getter;

@Getter
public class RatingStatRow {
    private Long questionId;
    private Integer score;
    private Long count;
    private Double averageScore;
}
