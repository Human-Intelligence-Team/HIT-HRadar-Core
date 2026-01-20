package org.hit.hradar.domain.evaluation.command.application.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class EvaluationAssignmentCreateRequest {

    private Long evaluatorId;
    private List<Long> evaluateeIds;
}
