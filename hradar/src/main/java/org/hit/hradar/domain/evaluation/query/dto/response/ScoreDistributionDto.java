package org.hit.hradar.domain.evaluation.query.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ScoreDistributionDto {

    private Integer score;
    private Long count;
}
