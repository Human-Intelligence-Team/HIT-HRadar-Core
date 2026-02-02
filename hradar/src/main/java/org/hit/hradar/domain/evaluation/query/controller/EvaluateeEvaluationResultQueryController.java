package org.hit.hradar.domain.evaluation.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.evaluation.query.dto.response.EvaluateeEvaluationResultResponse;
import org.hit.hradar.domain.evaluation.query.service.EvaluateeEvaluationResultQueryService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Evaluation Result Query", description = "피평가자 기준 평가 결과 조회 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/evaluation-results")
public class EvaluateeEvaluationResultQueryController {

        private final EvaluateeEvaluationResultQueryService queryService;

        // 피평가자 기준 평가 결과 조회
        @Operation(summary = "피평가자별 평가 결과 조회", description = "피평가자 ID와 회차, 평가 유형을 기준으로 평가 결과를 조회합니다.")
        @GetMapping("/evaluatee/{evaluateeId}")
        public ApiResponse<EvaluateeEvaluationResultResponse> getEvaluateeResult(
                        @PathVariable Long evaluateeId,
                        @RequestParam Long cycleId,
                        @RequestParam Long evalTypeId) {
                return ApiResponse.success(
                                queryService.getEvaluateeResult(evaluateeId, cycleId, evalTypeId));
        }

        // 나 기준 평가 결과 조회
        @Operation(summary = "내 평가 결과 조회", description = "로그인한 사용자의 본인 평가 결과를 회차와 평가 유형 기준으로 조회합니다.")
        @GetMapping("/evaluatee/me")
        public ApiResponse<EvaluateeEvaluationResultResponse> getMyResult(
                        @CurrentUser AuthUser authUser,
                        @RequestParam Long cycleId,
                        @RequestParam Long evalTypeId) {
                return ApiResponse.success(
                                queryService.getEvaluateeResult(authUser.employeeId(), cycleId, evalTypeId));
        }
}
