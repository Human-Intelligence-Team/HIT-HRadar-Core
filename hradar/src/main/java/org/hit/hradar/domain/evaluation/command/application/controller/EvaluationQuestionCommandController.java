package org.hit.hradar.domain.evaluation.command.application.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.evaluation.command.application.dto.request.EvaluationQuestionCreateRequest;
import org.hit.hradar.domain.evaluation.command.application.dto.request.EvaluationQuestionUpdateRequest;
import org.hit.hradar.domain.evaluation.command.application.service.EvaluationQuestionCommandService;
import org.hit.hradar.global.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class EvaluationQuestionCommandController {

    private final EvaluationQuestionCommandService questionCommandService;

    @PostMapping("/sections/{sectionId}/question")
    public ResponseEntity<ApiResponse<String>> createQuestion (
            @PathVariable Long sectionId,
            @RequestBody EvaluationQuestionCreateRequest request
    ){
        questionCommandService.createQuestion(sectionId, request);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @PutMapping("/questions/{questionId}")
    public ResponseEntity<ApiResponse<String>> updateQuestion (
            @PathVariable Long questionId,
            @RequestBody EvaluationQuestionUpdateRequest request
    ){
        questionCommandService.updateQuestion(questionId, request);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @DeleteMapping("/questions/{questionId}")
    public ResponseEntity<ApiResponse<String>> deleteQuestion (
            @PathVariable Long questionId
    ){
        questionCommandService.deleteQuestion(questionId);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
