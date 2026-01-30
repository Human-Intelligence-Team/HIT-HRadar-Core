package org.hit.hradar.domain.evaluation.command.application.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.evaluation.command.application.dto.request.EvaluationTypeCreateRequest;
import org.hit.hradar.domain.evaluation.command.application.dto.request.EvaluationTypeUpdateRequest;
import org.hit.hradar.domain.evaluation.command.application.service.EvaluationTypeCommandService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/evaluation-types")
public class EvaluationTypeCommandController {

    private final EvaluationTypeCommandService evaluationTypeCommandService;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> create(
            @CurrentUser AuthUser authUser,
            @RequestBody EvaluationTypeCreateRequest request
    ) {
        Long evalTypeId = evaluationTypeCommandService.create(authUser.companyId(), request);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    /**
     * 평가 유형 수정
     */
    @PutMapping("/{evalTypeId}")
    public ResponseEntity<ApiResponse<Void>> update(
            @PathVariable Long evalTypeId,
            @RequestBody EvaluationTypeUpdateRequest request
    ) {
        evaluationTypeCommandService.update(evalTypeId, request);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    /**
     * 평가 유형 삭제
     */
    @DeleteMapping("/{evalTypeId}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Long evalTypeId
    ) {
        evaluationTypeCommandService.delete(evalTypeId);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
