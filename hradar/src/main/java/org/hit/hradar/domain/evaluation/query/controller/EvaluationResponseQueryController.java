package org.hit.hradar.domain.evaluation.query.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.evaluation.query.dto.response.EvaluationResponseResultDto;
import org.hit.hradar.domain.evaluation.query.service.EvaluationResponseQueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/evaluation/results")
public class EvaluationResponseQueryController {
    private final EvaluationResponseQueryService  responseQueryService;

    @GetMapping("/types/{evalTypeId}")
    public List<EvaluationResponseResultDto> getResults(
            @PathVariable Long evalTypeId
    ) {
        return responseQueryService.getResults(evalTypeId);
    }
}
