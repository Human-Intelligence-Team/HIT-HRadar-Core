package org.hit.hradar.domain.evaluation.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.evaluation.command.application.dto.request.EvaluationTypeCreateRequest;
import org.hit.hradar.domain.evaluation.command.application.dto.request.EvaluationTypeUpdateRequest;
import org.hit.hradar.domain.evaluation.command.application.service.EvaluationTypeCommandService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Evaluation Type Command", description = "평가 유형 관리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/evaluation-types")
public class EvaluationTypeCommandController {

    private final EvaluationTypeCommandService evaluationTypeCommandService;

    @Operation(summary = "평가 유형 생성", description = "새로운 평가 유형을 생성합니다.")
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> create(
            @CurrentUser AuthUser authUser,
            @RequestBody EvaluationTypeCreateRequest request) {
        Long evalTypeId = evaluationTypeCommandService.create(authUser.companyId(), request);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    /**
     * 평가 유형 수정
     */
    @Operation(summary = "평가 유형 수정", description = "기존 평가 유형의 정보를 수정합니다.")
    @PutMapping("/{evalTypeId}")
    public ResponseEntity<ApiResponse<Void>> update(
            @PathVariable Long evalTypeId,
            @RequestBody EvaluationTypeUpdateRequest request) {
        evaluationTypeCommandService.update(evalTypeId, request);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    /**
     * 평가 유형 삭제
     */
    @Operation(summary = "평가 유형 삭제", description = "평가 유형을 삭제합니다.")
    @DeleteMapping("/{evalTypeId}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Long evalTypeId) {
        evaluationTypeCommandService.delete(evalTypeId);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
