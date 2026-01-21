package org.hit.hradar.domain.evaluation.command.application.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EvaluationSectionCreateRequest {

    private Long evalTypeId;
    private String sectionTitle;
    private String sectionDescription;
    private Integer sectionOrder;

}
