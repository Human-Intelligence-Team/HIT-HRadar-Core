package org.hit.hradar.domain.evaluation.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.evaluation.command.application.dto.request.CycleEvaluationTypeSaveRequest;
import org.hit.hradar.domain.evaluation.command.application.service.CycleEvaluationTypeCommandService;
import org.hit.hradar.global.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Evaluation Cycle Type Command", description = "회차별 평가 유형 관리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/evaluation-cycles")
public class CycleEvaluationTypeCommandController {

    private final CycleEvaluationTypeCommandService cycleEvaluationTypeCommandService;

    /**
     * 회차별 평가 유형 저장 (체크박스 방식)
     */
    @Operation(summary = "회차별 평가 유형 저장", description = "특정 회차에 적용할 평가 유형들을 저장합니다.")
    @PostMapping("/{cycleId}/types")
    public ResponseEntity<ApiResponse<Void>> save(
            @PathVariable Long cycleId,
            @RequestBody CycleEvaluationTypeSaveRequest request) {
        cycleEvaluationTypeCommandService.save(cycleId, request);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
