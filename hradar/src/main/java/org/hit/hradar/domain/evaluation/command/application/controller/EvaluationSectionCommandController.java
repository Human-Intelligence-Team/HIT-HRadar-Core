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
@RequestMapping("/types")
public class EvaluationSectionCommandController {

    private final EvaluationSectionCommandService sectionCommandService;

    //섹션 생성
    @PostMapping("/{typeId}/sections")
    public ResponseEntity<ApiResponse<String>> createSection(
            @PathVariable Long typeId,
            @RequestBody EvaluationSectionCreateRequest request
    ){
        sectionCommandService.createSection(typeId, request);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    //섹션 수정
    @PutMapping("/sections/{sectionId}")
    public ResponseEntity<ApiResponse<String>> updateSection(
            @PathVariable Long sectionId,
            @RequestBody EvaluationSectionUpdateRequest request
    ) {
        sectionCommandService.updateSection(sectionId, request);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    //섹션 삭제
    @DeleteMapping("/sections/{sectionId}")
    public ResponseEntity<ApiResponse<String>> deleteSection(
            @PathVariable Long sectionId
    ) {
        sectionCommandService.deleteSection(sectionId);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
