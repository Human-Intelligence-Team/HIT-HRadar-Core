package org.hit.hradar.domain.evaluation.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.evaluation.query.dto.response.RejectionCountChartResponse;
import org.hit.hradar.domain.evaluation.query.service.RejectionChartQueryService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Evaluation Dashboard - Rejection", description = "개인 대시보드 - 평가 반려 현황 조회 API")
@RestController
@RequestMapping("/my-dashboard/rejections")
@RequiredArgsConstructor
public class MyDashboardRejectionChartQueryController {

        private final RejectionChartQueryService service;

        @Operation(summary = "내 월간 반려 현황 조회", description = "현재 로그인한 사용자의 월간 평가 반려 건수를 조회합니다.")
        @GetMapping("/monthly")
        public ResponseEntity<ApiResponse<RejectionCountChartResponse>> getMyMonthlyRejections(
                        @CurrentUser AuthUser authUser,
                        @RequestParam String startYm,
                        @RequestParam String endYm) {
                RejectionCountChartResponse result = service.getMonthlyRejectionChart(
                                authUser.employeeId(),
                                startYm,
                                endYm);

                return ResponseEntity.ok(ApiResponse.success(result));
        }

        @Operation(summary = "사원별 월간 반려 현황 조회", description = "특정 사원의 월간 평가 반려 건수를 조회합니다.")
        @GetMapping("/monthly/emp")
        public ResponseEntity<ApiResponse<RejectionCountChartResponse>> getEmpMonthlyRejections(
                        @RequestParam Long empId,
                        @RequestParam String startYm,
                        @RequestParam String endYm) {
                RejectionCountChartResponse result = service.getMonthlyRejectionChart(
                                empId,
                                startYm,
                                endYm);

                return ResponseEntity.ok(ApiResponse.success(result));
        }
}
