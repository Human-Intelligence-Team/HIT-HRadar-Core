package org.hit.hradar.domain.evaluation.query.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.evaluation.query.dto.response.EvaluationSectionResponseDto;
import org.hit.hradar.domain.evaluation.query.service.EvaluationSectionQueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/*섹션만 조회*/
@RestController
@RequiredArgsConstructor
@RequestMapping("/evaluation-types")
public class EvaluationSectionQueryController {

    private final EvaluationSectionQueryService sectionQueryService;

    @GetMapping("/{evalTypeId}/sections")
    public List<EvaluationSectionResponseDto> getSections(
            @PathVariable Long evalTypeId) {
        return sectionQueryService.getSectionsByEvalType(evalTypeId);
    }
}
