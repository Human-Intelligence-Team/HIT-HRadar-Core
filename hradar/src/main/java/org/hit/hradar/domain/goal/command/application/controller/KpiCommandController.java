package org.hit.hradar.domain.goal.command.application.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.goal.command.application.dto.request.CreateKpiRequest;
import org.hit.hradar.domain.goal.command.application.service.KpiCommandService;
import org.hit.hradar.global.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/goal/{goalId}/kpi")
public class KpiCommandController {

    private final KpiCommandService kpiCommandService;

    @PostMapping("/details")
    public ResponseEntity<ApiResponse<String>> createKpi(
            @PathVariable Long goalId,
            @RequestBody CreateKpiRequest request
    ){
        Long kpiId = kpiCommandService.createKpi(goalId, request);
        return ResponseEntity.ok(ApiResponse.success(kpiId.toString()));
    }
}
