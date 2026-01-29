package org.hit.hradar.domain.evaluation.query.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class EvaluationSectionResponse {

    private Long sectionId;
    private String sectionTitle;
    private String sectionDescription;
    private Integer sectionOrder;

    private List<EvaluationQuestionResponse> questions;

}
