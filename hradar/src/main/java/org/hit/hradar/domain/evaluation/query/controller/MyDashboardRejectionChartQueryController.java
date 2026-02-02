package org.hit.hradar.domain.evaluation.query.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.evaluation.query.dto.response.RejectionCountChartResponse;
import org.hit.hradar.domain.evaluation.query.service.RejectionChartQueryService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/my-dashboard/rejections")
@RequiredArgsConstructor
public class MyDashboardRejectionChartQueryController {

    private final RejectionChartQueryService service;

    @GetMapping("/monthly")
    public ResponseEntity<ApiResponse<RejectionCountChartResponse>> getMyMonthlyRejections(
            @CurrentUser AuthUser authUser,
            @RequestParam String startYm,
            @RequestParam String endYm
    ) {
        RejectionCountChartResponse result =
                service.getMonthlyRejectionChart(
                        authUser.employeeId(),
                        startYm,
                        endYm
                );

        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @GetMapping("/monthly/emp")
    public ResponseEntity<ApiResponse<RejectionCountChartResponse>> getEmpMonthlyRejections(
            @RequestParam Long empId,
            @RequestParam String startYm,
            @RequestParam String endYm
    ) {
        RejectionCountChartResponse result =
                service.getMonthlyRejectionChart(
                        empId,
                        startYm,
                        endYm
                );

        return ResponseEntity.ok(ApiResponse.success(result));
    }
}
