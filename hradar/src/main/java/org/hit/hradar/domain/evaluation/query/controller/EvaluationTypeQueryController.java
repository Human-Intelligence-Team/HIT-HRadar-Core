package org.hit.hradar.domain.evaluation.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.evaluation.query.dto.response.EvaluationTypeResponse;
import org.hit.hradar.domain.evaluation.query.service.EvaluationTypeQueryService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Evaluation Type", description = "평가 종류 조회 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/evaluation-types")
public class EvaluationTypeQueryController {

    private final EvaluationTypeQueryService evaluationTypeQueryService;

    @Operation(summary = "평가 종류 목록 조회", description = "회사의 모든 평가 종류 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<ApiResponse<List<EvaluationTypeResponse>>> getEvaluationTypes(
            @CurrentUser AuthUser authUser) {
        List<EvaluationTypeResponse> result = evaluationTypeQueryService.getEvaluationTypes(authUser.companyId());

        return ResponseEntity.ok(ApiResponse.success(result));
    }
}
