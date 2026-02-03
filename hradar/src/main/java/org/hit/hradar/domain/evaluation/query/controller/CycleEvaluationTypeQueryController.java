package org.hit.hradar.domain.evaluation.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.evaluation.query.dto.response.CycleEvaluationTypeResponse;
import org.hit.hradar.domain.evaluation.query.service.CycleEvaluationTypeQueryService;
import org.hit.hradar.global.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Evaluation Cycle Type Query", description = "회차별 평가 유형 조회 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/evaluation-cycles")
public class CycleEvaluationTypeQueryController {

    private final CycleEvaluationTypeQueryService cycleEvaluationTypeQueryService;

    /**
     * 회차별 평가 유형 조회
     */
    @Operation(summary = "회차별 평가 유형 목록 조회", description = "특정 회차에서 사용하는 모든 평가 유형 목록을 조회합니다.")
    @GetMapping("/{cycleId}/types")
    public ResponseEntity<ApiResponse<List<CycleEvaluationTypeResponse>>> getEvaluationTypes(
            @PathVariable Long cycleId) {
        List<CycleEvaluationTypeResponse> result = cycleEvaluationTypeQueryService.getEvaluationTypes(cycleId);

        return ResponseEntity.ok(ApiResponse.success(result));
    }
}
