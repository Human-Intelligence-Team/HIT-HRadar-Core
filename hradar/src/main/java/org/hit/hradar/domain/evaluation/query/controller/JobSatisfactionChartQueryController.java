package org.hit.hradar.domain.evaluation.query.controller;

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

@RestController
@RequiredArgsConstructor
@RequestMapping("/my-dashboard")
public class JobSatisfactionChartQueryController {

    private final JobSatisfactionChartQueryService jobSatisfactionChartQueryService;

    @GetMapping("/job-satisfaction")
    public ResponseEntity<ApiResponse<JobSatisfactionChartResponse>> getMyJobSatisfactionChart(
            @CurrentUser AuthUser authUser
    ) {
        JobSatisfactionChartResponse result =
                jobSatisfactionChartQueryService.getMyJobSatisfactionChart(authUser.employeeId());

        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @GetMapping("/job-satisfaction/emp")
    public ResponseEntity<ApiResponse<JobSatisfactionChartResponse>> jobSatisfactionByEmp(
            @RequestParam Long empId
    ) {
        JobSatisfactionChartResponse result =
                jobSatisfactionChartQueryService.getMyJobSatisfactionChart(empId);

        return ResponseEntity.ok(ApiResponse.success(result));
    }

}
