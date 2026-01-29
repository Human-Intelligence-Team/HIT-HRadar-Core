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
@RequestMapping("/evaluation-sections")
public class EvaluationQuestionCommandController {

    private final EvaluationQuestionCommandService questionCommandService;

    @PostMapping("/{sectionId}/questions")
    public ResponseEntity<ApiResponse<Long>> createQuestion(
            @PathVariable Long sectionId,
            @RequestBody EvaluationQuestionCreateRequest request
    ) {
        request.setSectionId(sectionId);

        Long questionId = questionCommandService.create(request);
        return ResponseEntity.ok(ApiResponse.success(questionId));
    }

    @PutMapping("/questions/{questionId}")
    public ResponseEntity<ApiResponse<Void>> updateQuestion(
            @PathVariable Long questionId,
            @RequestBody EvaluationQuestionUpdateRequest request
    ) {
        questionCommandService.update(questionId, request);
        return ResponseEntity.ok(ApiResponse.success(null));
    }


    @DeleteMapping("/questions/{questionId}")
    public ResponseEntity<ApiResponse<Void>> deleteQuestion(
            @PathVariable Long questionId
    ) {
        questionCommandService.deleteQuestion(questionId);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
