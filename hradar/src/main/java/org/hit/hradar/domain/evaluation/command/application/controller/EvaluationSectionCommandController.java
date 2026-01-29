package org.hit.hradar.domain.evaluation.command.application.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.evaluation.command.application.dto.request.EvaluationSectionCreateRequest;
import org.hit.hradar.domain.evaluation.command.application.dto.request.EvaluationSectionUpdateRequest;
import org.hit.hradar.domain.evaluation.command.application.service.EvaluationSectionCommandService;
import org.hit.hradar.global.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cycle-evaluation-types")
public class EvaluationSectionCommandController {

    private final EvaluationSectionCommandService evaluationSectionCommandService;

    //섹션생성
    @PostMapping("/{cycleEvalTypeId}/sections")
    public ResponseEntity<ApiResponse<Long>> create(
            @PathVariable Long cycleEvalTypeId,
            @RequestBody EvaluationSectionCreateRequest request
    ) {
        Long sectionId =
                evaluationSectionCommandService.create(cycleEvalTypeId, request);

        return ResponseEntity.ok(ApiResponse.success(sectionId));
    }

    //섹션 수정
    @PutMapping("/sections/{sectionId}")
    public ResponseEntity<ApiResponse<Void>> update(
            @PathVariable Long sectionId,
            @RequestBody EvaluationSectionUpdateRequest request
    ) {
        evaluationSectionCommandService.update(sectionId, request);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    //섹션 삭제
    @DeleteMapping("/sections/{sectionId}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Long sectionId
    ) {
        evaluationSectionCommandService.delete(sectionId);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
