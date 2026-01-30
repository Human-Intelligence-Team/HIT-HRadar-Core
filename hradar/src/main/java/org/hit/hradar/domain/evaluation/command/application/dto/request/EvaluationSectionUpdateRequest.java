package org.hit.hradar.domain.evaluation.command.application.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class EvaluationSectionUpdateRequest {

    private String sectionTitle;
    private String sectionDescription;
    private Integer sectionOrder;

}
