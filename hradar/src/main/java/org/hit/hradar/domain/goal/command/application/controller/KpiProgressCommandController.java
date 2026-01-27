package org.hit.hradar.domain.goal.command.application.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.goal.command.application.dto.request.CreateKpiProgressRequest;
import org.hit.hradar.domain.goal.command.application.service.KpiProgressCommandService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/goals/{goalId}/kpis")
public class KpiProgressCommandController {

    private final KpiProgressCommandService kpiProgressCommandService;

    @PostMapping("/{kpiId}/progress")
    public ResponseEntity<ApiResponse<String>> createProgress(
            @PathVariable Long kpiId,
            @CurrentUser AuthUser authUser,
            @RequestBody CreateKpiProgressRequest request
    ) {
        kpiProgressCommandService.createProgress(kpiId, authUser.employeeId(), request);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
