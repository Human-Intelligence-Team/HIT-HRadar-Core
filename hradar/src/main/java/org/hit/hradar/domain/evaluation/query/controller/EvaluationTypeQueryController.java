package org.hit.hradar.domain.evaluation.query.controller;

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

@RestController
@RequiredArgsConstructor
@RequestMapping("/evaluation-types")
public class EvaluationTypeQueryController {

    private final EvaluationTypeQueryService evaluationTypeQueryService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<EvaluationTypeResponse>>> getEvaluationTypes(
            @CurrentUser AuthUser authUser
    ) {
        List<EvaluationTypeResponse> result =
                evaluationTypeQueryService.getEvaluationTypes(authUser.companyId());

        return ResponseEntity.ok(ApiResponse.success(result));
    }
}
