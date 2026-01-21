package org.hit.hradar.domain.evaluation.query.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EvaluationSectionResponseDto {

    private Long sectionId;
    private String sectionTitle;
    private String sectionDescription;
    private Integer sectionOrder;
}
