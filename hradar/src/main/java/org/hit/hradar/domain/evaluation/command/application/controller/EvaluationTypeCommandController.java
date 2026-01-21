package org.hit.hradar.domain.evaluation.command.application.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.evaluation.command.application.dto.request.EvaluationTypeAddRequest;
import org.hit.hradar.domain.evaluation.command.application.service.EvaluationTypeCommandService;
import org.hit.hradar.global.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/evaluation-cycles")
public class EvaluationTypeCommandController {

    private final EvaluationTypeCommandService evaluationTypeCommandService;

    //회차 평가 유형 추가
    @PostMapping("/{cycleId}/types")
    public ResponseEntity<ApiResponse<String>> addEvaluationType(
            @PathVariable Long cycleId,
            @RequestBody EvaluationTypeAddRequest request
    ) {
        evaluationTypeCommandService.addEvaluationType(cycleId, request);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    //회차 평가 유형 제거
    @DeleteMapping("/types/{evalTypeId}")
    public ResponseEntity<ApiResponse<String>> removeEvaluationType(
            @PathVariable Long evalTypeId
    ) {
        evaluationTypeCommandService.removeEvaluationType(evalTypeId);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
