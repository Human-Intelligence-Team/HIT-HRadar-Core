package org.hit.hradar.domain.evaluation.command.application.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.evaluation.command.application.dto.request.EvaluationAssignmentCreateRequest;
import org.hit.hradar.domain.evaluation.command.application.service.EvaluationAssignmentCommandService;
import org.hit.hradar.global.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/types")
public class EvaluationAssignmentCommandController {

    private final EvaluationAssignmentCommandService assignmentCommandService;

    //평가 배정 생성
    @PostMapping("/{evalTypeId}/assignments")
    public ResponseEntity<ApiResponse<String>> createAssignments(
            @PathVariable Long evalTypeId,
            @RequestBody EvaluationAssignmentCreateRequest request
    ) {
        assignmentCommandService.createAssignments(evalTypeId, request);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    // 평가 배정은 수정 없이 재배정으로

    // 평가 배정 취소
    @PostMapping("/assignments/{assignmentId}/delete")
    public ResponseEntity<ApiResponse<String>> deleteAssignments(
            @PathVariable Long assignmentId
    ){
        assignmentCommandService.deleteAssignments(assignmentId);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
