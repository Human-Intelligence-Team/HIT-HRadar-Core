package org.hit.hradar.domain.evaluation.command.application.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.evaluation.command.application.dto.request.EvaluationResponseUpsertRequest;
import org.hit.hradar.domain.evaluation.command.application.service.EvaluationResponseCommandService;
import org.hit.hradar.domain.evaluation.command.application.service.EvaluationSubmissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/evaluation-responses")
public class EvaluationResponseCommandController {
    private final EvaluationResponseCommandService responseCommandService;
    private final EvaluationSubmissionService submissionService;

    @PutMapping("/draft")
    public ResponseEntity<Void> saveDraft(
            @RequestBody EvaluationResponseUpsertRequest request
    ) {
        responseCommandService.saveDraft(request);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/{assignmentId}/submit")
    public ResponseEntity<Void> submit(
            @PathVariable Long assignmentId
    ) {
        submissionService.submit(assignmentId);
        return ResponseEntity.ok().build();
    }
}
