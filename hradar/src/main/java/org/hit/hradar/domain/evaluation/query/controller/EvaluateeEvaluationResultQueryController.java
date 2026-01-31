package org.hit.hradar.domain.evaluation.query.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.evaluation.query.dto.response.EvaluateeEvaluationResultResponse;
import org.hit.hradar.domain.evaluation.query.service.EvaluateeEvaluationResultQueryService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/evaluation-results")
public class EvaluateeEvaluationResultQueryController {

    private final EvaluateeEvaluationResultQueryService queryService;

    //피평가자 기준 평가 결과 조회
    @GetMapping("/evaluatee/{evaluateeId}")
    public ApiResponse<EvaluateeEvaluationResultResponse> getEvaluateeResult(
            @PathVariable Long evaluateeId,
            @RequestParam Long cycleId,
            @RequestParam Long evalTypeId
    ) {
        return ApiResponse.success(
                queryService.getEvaluateeResult(evaluateeId, cycleId, evalTypeId)
        );
    }

    //나 기준 평가 결과 조회
    @GetMapping("/evaluatee/me")
    public ApiResponse<EvaluateeEvaluationResultResponse> getMyResult(
            @CurrentUser AuthUser authUser,
            @RequestParam Long cycleId,
            @RequestParam Long evalTypeId
    ) {
        return ApiResponse.success(
                queryService.getEvaluateeResult(authUser.employeeId(), cycleId, evalTypeId)
        );
    }
}
