package org.hit.hradar.domain.evaluation.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.evaluation.command.application.dto.request.EvaluationResponseUpsertRequest;
import org.hit.hradar.domain.evaluation.command.application.service.EvaluationResponseCommandService;
import org.hit.hradar.domain.evaluation.command.application.service.EvaluationSubmissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Evaluation Response Command", description = "평가 응답 관리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/evaluation-responses")
public class EvaluationResponseCommandController {
    private final EvaluationResponseCommandService responseCommandService;
    private final EvaluationSubmissionService submissionService;

    @Operation(summary = "평가 응답 임시저장", description = "작성 중인 평가 답변을 임시저장합니다.")
    @PutMapping("/draft")
    public ResponseEntity<Void> saveDraft(
            @RequestBody EvaluationResponseUpsertRequest request) {
        responseCommandService.saveDraft(request);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "평가 최종 제출", description = "작성한 평가를 최종 제출하여 확정합니다.")
    @PostMapping("/{assignmentId}/submit")
    public ResponseEntity<Void> submit(
            @PathVariable Long assignmentId) {
        submissionService.submit(assignmentId);
        return ResponseEntity.ok().build();
    }
}
