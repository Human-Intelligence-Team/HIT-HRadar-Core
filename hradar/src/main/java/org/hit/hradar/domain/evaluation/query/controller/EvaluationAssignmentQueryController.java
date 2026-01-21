package org.hit.hradar.domain.evaluation.query.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.evaluation.query.dto.response.EvaluationAssignmentListResponseDto;
import org.hit.hradar.domain.evaluation.query.dto.response.EvaluationAssignmentResponseDto;
import org.hit.hradar.domain.evaluation.query.service.EvaluationAssignmentQueryService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/evaluation-assignments")
public class EvaluationAssignmentQueryController {
    private final EvaluationAssignmentQueryService assignmentQueryService;

    //내가 평가해야할 목록 조회
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<List<EvaluationAssignmentResponseDto>>> getMyAssignments(
            @CurrentUser AuthUser authUser
    ) {
        Long employeeId = authUser.employeeId();

        return ResponseEntity.ok(
                ApiResponse.success(
                        assignmentQueryService.getMyAssignments(employeeId)
                )
        );
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<EvaluationAssignmentListResponseDto>>> getAssignments(
            @RequestParam Long cycleId,
            @RequestParam(required = false) String evalTypeCode,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Long evaluatorId,
            @RequestParam(required = false) Long evaluateeId
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        assignmentQueryService.getAssignments(
                                cycleId,
                                evalTypeCode,
                                status,
                                evaluatorId,
                                evaluateeId
                        )
                )
        );
    }



}
