package org.hit.hradar.domain.evaluation.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.evaluation.query.dto.response.JobSatisfactionChartResponse;
import org.hit.hradar.domain.evaluation.query.service.JobSatisfactionChartQueryService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Evaluation Dashboard - Job Satisfaction", description = "개인 대시보드 - 직무 만족도 차트 조회 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/my-dashboard")
public class JobSatisfactionChartQueryController {

        private final JobSatisfactionChartQueryService jobSatisfactionChartQueryService;

        @Operation(summary = "내 직무 만족도 차트 조회", description = "현재 로그인한 사용자의 직무 만족도 데이터를 차트 형태로 조회합니다.")
        @GetMapping("/job-satisfaction")
        public ResponseEntity<ApiResponse<JobSatisfactionChartResponse>> getMyJobSatisfactionChart(
                        @CurrentUser AuthUser authUser) {
                JobSatisfactionChartResponse result = jobSatisfactionChartQueryService
                                .getMyJobSatisfactionChart(authUser.employeeId());

                return ResponseEntity.ok(ApiResponse.success(result));
        }

        @Operation(summary = "사원별 직무 만족도 차트 조회", description = "특정 사원의 직무 만족도 데이터를 조회합니다 (관리자 또는 본인용).")
        @GetMapping("/job-satisfaction/emp")
        public ResponseEntity<ApiResponse<JobSatisfactionChartResponse>> jobSatisfactionByEmp(
                        @RequestParam Long empId) {
                JobSatisfactionChartResponse result = jobSatisfactionChartQueryService.getMyJobSatisfactionChart(empId);

                return ResponseEntity.ok(ApiResponse.success(result));
        }

}
