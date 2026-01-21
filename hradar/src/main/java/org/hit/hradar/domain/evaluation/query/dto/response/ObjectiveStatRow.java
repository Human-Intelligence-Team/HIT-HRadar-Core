package org.hit.hradar.domain.evaluation.query.dto.response;

import lombok.Getter;

@Getter
public class ObjectiveStatRow {
    private Long questionId;
    private Long optionId;
    private String optionContent;
    private Long count;
}
