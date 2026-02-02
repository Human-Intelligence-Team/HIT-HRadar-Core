package org.hit.hradar.domain.goal.query.controller;

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

@RestController
@RequestMapping("/my-dashboard/contribution")
@RequiredArgsConstructor
public class MyDashboardContributionQueryController {

    private final ContributionQueryService contributionQueryService;

    @GetMapping
    public ResponseEntity<ApiResponse<ContributionBarChartResponse>> getMyContribution(
            @CurrentUser AuthUser authUser
    ){
        ContributionBarChartResponse result = contributionQueryService.getMuContributionBarChart(authUser.employeeId());
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @GetMapping("/emp")
    public ResponseEntity<ApiResponse<ContributionBarChartResponse>> getContributionByEmp(
            @RequestParam Long empId
    ){
        ContributionBarChartResponse result = contributionQueryService.getMuContributionBarChart(empId);
        return ResponseEntity.ok(ApiResponse.success(result));
    }
}
