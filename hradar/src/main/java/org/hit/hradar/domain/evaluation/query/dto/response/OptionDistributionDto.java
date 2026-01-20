package org.hit.hradar.domain.evaluation.query.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OptionDistributionDto {

    private Long optionId;
    private String optionContent;
    private Long count;
}
