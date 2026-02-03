package org.hit.hradar.domain.goal.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.goal.query.dto.response.ContributionBarChartResponse;
import org.hit.hradar.domain.goal.query.service.ContributionQueryService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Goal Dashboard - Contribution", description = "개인 대시보드 - 목표 기여도 조회 API")
@RestController
@RequestMapping("/my-dashboard/contribution")
@RequiredArgsConstructor
public class MyDashboardContributionQueryController {

    private final ContributionQueryService contributionQueryService;

    @Operation(summary = "내 목표 기여도 조회", description = "현재 로그인한 사용자의 목표 달성 기여도를 부서 내 점유율 형식으로 조회합니다.")
    @GetMapping
    public ResponseEntity<ApiResponse<ContributionBarChartResponse>> getMyContribution(
            @CurrentUser AuthUser authUser) {
        ContributionBarChartResponse result = contributionQueryService.getMuContributionBarChart(authUser.employeeId());
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @Operation(summary = "사원별 목표 기여도 조회", description = "특정 사원의 목표 기여도 데이터를 조회합니다.")
    @GetMapping("/emp")
    public ResponseEntity<ApiResponse<ContributionBarChartResponse>> getContributionByEmp(
            @RequestParam Long empId) {
        ContributionBarChartResponse result = contributionQueryService.getMuContributionBarChart(empId);
        return ResponseEntity.ok(ApiResponse.success(result));
    }
}
