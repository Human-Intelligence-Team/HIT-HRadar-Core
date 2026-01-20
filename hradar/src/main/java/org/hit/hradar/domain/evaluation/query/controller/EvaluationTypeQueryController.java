package org.hit.hradar.domain.evaluation.query.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.evaluation.query.dto.response.EvaluationTypeResponseDto;
import org.hit.hradar.domain.evaluation.query.service.EvaluationTypeQueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/evaluation-cycles")
public class EvaluationTypeQueryController {

    private final EvaluationTypeQueryService evaluationTypeQueryService;

    //회차 선택시 회차에 포함된 평가유형 목록조회
    @GetMapping("/{cycleId}/types")
    public List<EvaluationTypeResponseDto> getEvaluationTypes(
            @PathVariable Long cycleId
    ) {
        return evaluationTypeQueryService.getEvaluationTypes(cycleId);
    }
}
