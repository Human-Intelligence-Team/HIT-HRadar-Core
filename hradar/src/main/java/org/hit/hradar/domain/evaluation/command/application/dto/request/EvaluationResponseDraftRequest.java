package org.hit.hradar.domain.evaluation.command.application.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class EvaluationResponseDraftRequest {

    private Long assignmentId;
    private List<EvaluationResponseSaveRequest> response;
}
