package org.hit.hradar.domain.evaluation.query.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ObjectiveOptionResponse {

    private Long optionId;
    private String optionContent;
    private Integer optionScore;


}
