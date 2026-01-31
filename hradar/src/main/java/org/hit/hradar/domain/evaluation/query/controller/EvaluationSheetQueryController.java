package org.hit.hradar.domain.evaluation.query.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.evaluation.query.dto.response.EvaluationSectionResponse;
import org.hit.hradar.domain.evaluation.query.service.EvaluationSheetQueryService;
import org.hit.hradar.global.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/evaluation-cycles")
public class EvaluationSheetQueryController {

    private final EvaluationSheetQueryService evaluationSheetQueryService;

    /**
     * 회차 + 평가유형 기준 문항지 조회
     *
     * [흐름]
     * 1. 회차 선택
     * 2. 해당 회차에 포함된 평가유형 선택
     * 3. 해당 평가유형에 설정된 문항지(섹션 + 문제 + 옵션) 조회
     */
    @GetMapping("/{cycleId}/types/{evalTypeId}/sections")
    public ResponseEntity<ApiResponse<List<EvaluationSectionResponse>>> getEvaluationSheet(
            @PathVariable Long cycleId,
            @PathVariable Long evalTypeId
    ) {
        List<EvaluationSectionResponse> result =
                evaluationSheetQueryService.getEvaluationSheet(cycleId, evalTypeId);

        return ResponseEntity.ok(ApiResponse.success(result));
    }
}
