package org.hit.hradar.domain.evaluation.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.evaluation.command.application.dto.request.EvaluationQuestionCreateRequest;
import org.hit.hradar.domain.evaluation.command.application.dto.request.EvaluationQuestionUpdateRequest;
import org.hit.hradar.domain.evaluation.command.application.service.EvaluationQuestionCommandService;
import org.hit.hradar.global.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Evaluation Question Command", description = "평가 문항 관리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/evaluation-sections")
public class EvaluationQuestionCommandController {

    private final EvaluationQuestionCommandService questionCommandService;

    @Operation(summary = "평가 문항 생성", description = "특정 섹션에 새로운 평가 문항을 추가합니다.")
    @PostMapping("/{sectionId}/questions")
    public ResponseEntity<ApiResponse<Long>> createQuestion(
            @PathVariable Long sectionId,
            @RequestBody EvaluationQuestionCreateRequest request) {
        request.setSectionId(sectionId);

        Long questionId = questionCommandService.create(request);
        return ResponseEntity.ok(ApiResponse.success(questionId));
    }

    @Operation(summary = "평가 문항 수정", description = "기존 평가 문항의 내용을 수정합니다.")
    @PutMapping("/questions/{questionId}")
    public ResponseEntity<ApiResponse<Void>> updateQuestion(
            @PathVariable Long questionId,
            @RequestBody EvaluationQuestionUpdateRequest request) {
        questionCommandService.update(questionId, request);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @Operation(summary = "평가 문항 삭제", description = "평가 문항을 삭제합니다.")
    @DeleteMapping("/questions/{questionId}")
    public ResponseEntity<ApiResponse<Void>> deleteQuestion(
            @PathVariable Long questionId) {
        questionCommandService.deleteQuestion(questionId);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
