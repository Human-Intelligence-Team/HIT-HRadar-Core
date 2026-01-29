package org.hit.hradar.domain.evaluation.command.application.dto.request;

import lombok.Getter;

@Getter
public class EvaluationSectionCreateRequest {

    private Long cycleEvalTypeId;

    private String sectionTitle;
    private String sectionDescription;
    private Integer sectionOrder;
}
