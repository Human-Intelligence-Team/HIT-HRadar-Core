package org.hit.hradar.domain.evaluation.command.application.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EvaluationTypeAddRequest {

    private String evalTypeCode; // EVAL_001, EVAL_002 ...

}
