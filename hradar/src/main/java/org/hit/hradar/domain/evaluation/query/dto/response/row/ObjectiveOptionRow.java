package org.hit.hradar.domain.evaluation.query.dto.response.row;

import lombok.Getter;

@Getter
public class ObjectiveOptionRow {

    private Long optionId;
    private Long questionId;

    private String optionContent;
    private Integer optionScore;
}
