package org.hit.hradar.domain.evaluation.command.application.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class CycleEvaluationTypeSaveRequest {

    private List<Long> evalTypeIds;
}
