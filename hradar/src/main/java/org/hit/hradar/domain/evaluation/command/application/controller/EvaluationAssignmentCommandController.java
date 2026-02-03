package org.hit.hradar.domain.evaluation.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.evaluation.command.application.dto.request.EvaluationAssignmentCreateRequest;
import org.hit.hradar.domain.evaluation.command.application.service.EvaluationAssignmentCommandService;
import org.hit.hradar.global.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Evaluation Assignment Command", description = "평가 대상자 배정 관리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/cycle-evaluation-types")
public class EvaluationAssignmentCommandController {

    private final EvaluationAssignmentCommandService assignmentCommandService;

    // 평가 배정 생성
    @Operation(summary = "평가 배정 생성", description = "특정 회차 평가 유형에 대해 평가 대상자들을 배정합니다.")
    @PostMapping("/{cycleEvalTypeId}/assignments")
    public ResponseEntity<ApiResponse<List<Long>>> createAssignments(
            @PathVariable Long cycleEvalTypeId,
            @RequestBody EvaluationAssignmentCreateRequest request) {
        List<Long> assignmentIds = assignmentCommandService.createAssignments(cycleEvalTypeId, request);
        return ResponseEntity.ok(ApiResponse.success(assignmentIds));
    }

    // 평가 배정 삭제
    @Operation(summary = "평가 배정 삭제", description = "기존의 평가 배정 정보를 삭제합니다.")
    @DeleteMapping("/assignments/{assignmentId}")
    public ResponseEntity<Void> deleteAssignment(
            @PathVariable Long assignmentId) {
        assignmentCommandService.deleteAssignment(assignmentId);
        return ResponseEntity.ok().build();
    }
}
