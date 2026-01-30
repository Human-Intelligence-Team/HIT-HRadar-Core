package org.hit.hradar.domain.evaluation.command.application.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class EvaluationResponseUpsertRequest {


    private Long assignmentId;


    private Long cycleId;


    private Long evalTypeId;


    private Long evaluateeId;

    private List<EvaluationResponseItemRequest> responses;
}
