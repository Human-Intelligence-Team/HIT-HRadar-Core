package org.hit.hradar.domain.evaluation.query.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.evaluation.query.dto.response.EvaluationQuestionResponseDto;
import org.hit.hradar.domain.evaluation.query.service.EvaluationQuestionQueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sections")
public class EvaluationQuestionQueryController {

    private final EvaluationQuestionQueryService questionQueryService;

    //섹션에 포함된 문제 조회
    @GetMapping("/{sectionId}/questions")
    public List<EvaluationQuestionResponseDto> getQuestions(
            @PathVariable("sectionId") Long sectionId
    ){
        return questionQueryService.getQuestions(sectionId);
    }
}
