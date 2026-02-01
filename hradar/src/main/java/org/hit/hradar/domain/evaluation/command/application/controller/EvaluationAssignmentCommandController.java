package org.hit.hradar.domain.evaluation.command.application.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.evaluation.command.application.dto.request.EvaluationAssignmentCreateRequest;
import org.hit.hradar.domain.evaluation.command.application.service.EvaluationAssignmentCommandService;
import org.hit.hradar.global.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cycle-evaluation-types")
public class EvaluationAssignmentCommandController {

    private final EvaluationAssignmentCommandService assignmentCommandService;

    //평가 배정 생성
    @PostMapping("/{cycleEvalTypeId}/assignments")
    public ResponseEntity<ApiResponse<List<Long>>> createAssignments(
            @PathVariable Long cycleEvalTypeId,
            @RequestBody EvaluationAssignmentCreateRequest request
    ){
        List<Long> assignmentIds =
                assignmentCommandService.createAssignments(cycleEvalTypeId, request);
        return ResponseEntity.ok(ApiResponse.success(assignmentIds));
    }

    //평가 배정 삭제
    @DeleteMapping("/assignments/{assignmentId}")
    public ResponseEntity<Void> deleteAssignment(
            @PathVariable Long assignmentId
    ) {
        assignmentCommandService.deleteAssignment(assignmentId);
        return ResponseEntity.ok().build();
    }
}
