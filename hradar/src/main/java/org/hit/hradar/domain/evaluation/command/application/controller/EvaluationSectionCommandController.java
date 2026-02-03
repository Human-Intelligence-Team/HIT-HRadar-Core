package org.hit.hradar.domain.evaluation.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.evaluation.command.application.dto.request.EvaluationSectionCreateRequest;
import org.hit.hradar.domain.evaluation.command.application.dto.request.EvaluationSectionUpdateRequest;
import org.hit.hradar.domain.evaluation.command.application.service.EvaluationSectionCommandService;
import org.hit.hradar.global.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Evaluation Section Command", description = "평가 섹션 관리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/cycle-evaluation-types")
public class EvaluationSectionCommandController {

    private final EvaluationSectionCommandService evaluationSectionCommandService;

    // 섹션생성
    @Operation(summary = "평가 섹션 생성", description = "특정 회차 평가 유형에 새로운 섹션을 추가합니다.")
    @PostMapping("/{cycleEvalTypeId}/sections")
    public ResponseEntity<ApiResponse<Long>> create(
            @PathVariable Long cycleEvalTypeId,
            @RequestBody EvaluationSectionCreateRequest request) {
        Long sectionId = evaluationSectionCommandService.create(cycleEvalTypeId, request);
        return ResponseEntity.ok(ApiResponse.success(sectionId));
    }

    // 섹션 수정
    @Operation(summary = "평가 섹션 수정", description = "기존 평가 섹션의 정보를 수정합니다.")
    @PutMapping("/sections/{sectionId}")
    public ResponseEntity<ApiResponse<Void>> update(
            @PathVariable Long sectionId,
            @RequestBody EvaluationSectionUpdateRequest request) {

        evaluationSectionCommandService.update(sectionId, request);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    // 섹션 삭제
    @Operation(summary = "평가 섹션 삭제", description = "평가 섹션을 삭제합니다.")
    @DeleteMapping("/sections/{sectionId}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Long sectionId) {
        evaluationSectionCommandService.delete(sectionId);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
