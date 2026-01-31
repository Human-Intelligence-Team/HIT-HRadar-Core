package org.hit.hradar.domain.evaluation.command.application.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.evaluation.command.application.dto.request.CycleEvaluationTypeSaveRequest;
import org.hit.hradar.domain.evaluation.command.application.service.CycleEvaluationTypeCommandService;
import org.hit.hradar.global.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/evaluation-cycles")
public class CycleEvaluationTypeCommandController {


    private final CycleEvaluationTypeCommandService cycleEvaluationTypeCommandService;

    /**
     * 회차별 평가 유형 저장 (체크박스 방식)
     */
    @PostMapping("/{cycleId}/types")
    public ResponseEntity<ApiResponse<Void>> save(
            @PathVariable Long cycleId,
            @RequestBody CycleEvaluationTypeSaveRequest request
    ) {
        cycleEvaluationTypeCommandService.save(cycleId, request);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
