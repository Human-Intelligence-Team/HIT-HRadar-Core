package org.hit.hradar.domain.evaluation.query.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.evaluation.query.dto.response.DepartmentEvaluationAssignmentDetailResponse;
import org.hit.hradar.domain.evaluation.query.dto.response.EvaluationAssignmentAdminResponse;
import org.hit.hradar.domain.evaluation.query.service.EvaluationAssignmentDeptDetailQueryService;
import org.hit.hradar.domain.evaluation.query.service.EvaluationAssignmentQueryService;
import org.hit.hradar.domain.evaluation.query.dto.response.EvaluationAssignmentResponse;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/evaluation-assignments")
public class EvaluationAssignmentQueryController {

    private final EvaluationAssignmentQueryService assignmentQueryService;
    private final EvaluationAssignmentDeptDetailQueryService queryService;

    //평가자 기준 평가 배정 조회
    @GetMapping("/evaluator")
    public ApiResponse<List<EvaluationAssignmentResponse>> getAssignmentsByEvaluator(
            @CurrentUser AuthUser authUser
    ) {
        return ApiResponse.success(
                assignmentQueryService.getAssignmentsByEvaluator(authUser.employeeId())
        );
    }

    //전체 조회
    @GetMapping("/cycle-eval-types/{cycleEvalTypeId}")
    public ApiResponse<List<EvaluationAssignmentAdminResponse>> getAssignments(
            @PathVariable Long cycleEvalTypeId
    ) {
        return ApiResponse.success(
                assignmentQueryService.getAssignments(cycleEvalTypeId)
        );
    }

    @GetMapping("/department/details")
    public ApiResponse<List<DepartmentEvaluationAssignmentDetailResponse>> getDeptAssignmentDetails(
            @RequestParam Long deptId
    ) {
        return ApiResponse.success(
                queryService.getDeptAssignmentDetails(deptId)
        );
    }
}
