package org.hit.hradar.domain.evaluation.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Evaluation Assignment Query", description = "평가 대상자 배정 조회 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/evaluation-assignments")
public class EvaluationAssignmentQueryController {

        private final EvaluationAssignmentQueryService assignmentQueryService;
        private final EvaluationAssignmentDeptDetailQueryService queryService;

        // 평가자 기준 평가 배정 조회
        @Operation(summary = "평가자별 배정 목록 조회", description = "로그인한 평가자에게 배정된 모든 피평가자 목록을 조회합니다.")
        @GetMapping("/evaluator")
        public ApiResponse<List<EvaluationAssignmentResponse>> getAssignmentsByEvaluator(
                        @CurrentUser AuthUser authUser) {
                return ApiResponse.success(
                                assignmentQueryService.getAssignmentsByEvaluator(authUser.employeeId()));
        }

        // 전체 조회
        @Operation(summary = "회차별 전체 배정 목록 조회", description = "특정 회차 평가 유형에 대한 모든 배정 현황을 조회합니다.")
        @GetMapping("/cycle-eval-types/{cycleEvalTypeId}")
        public ApiResponse<List<EvaluationAssignmentAdminResponse>> getAssignments(
                        @PathVariable Long cycleEvalTypeId) {
                return ApiResponse.success(
                                assignmentQueryService.getAssignments(cycleEvalTypeId));
        }

        @Operation(summary = "부서별 배정 상세 현황 조회", description = "특정 부서의 평가 배정 상세 현황을 조회합니다.")
        @GetMapping("/department/details")
        public ApiResponse<List<DepartmentEvaluationAssignmentDetailResponse>> getDeptAssignmentDetails(
                        @RequestParam Long deptId) {
                return ApiResponse.success(
                                queryService.getDeptAssignmentDetails(deptId));
        }
}
