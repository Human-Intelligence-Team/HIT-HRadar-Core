package org.hit.hradar.domain.goal.command.application.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.goal.command.application.dto.request.CreateKpiRequest;
import org.hit.hradar.domain.goal.command.application.dto.request.ResubmitKpiRequest;
import org.hit.hradar.domain.goal.command.application.dto.request.UpdateKpiRequest;
import org.hit.hradar.domain.goal.command.application.service.KpiCommandService;
import org.hit.hradar.global.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/goals/{goalId}/kpis")
public class KpiCommandController {

    private final KpiCommandService kpiCommandService;

    //KPI 등록
    @PostMapping()
    public ResponseEntity<ApiResponse<String>> createKpi(
            @PathVariable Long goalId,
            @RequestBody CreateKpiRequest request
    ){
        Long kpiId = kpiCommandService.createKpi(goalId, request);
        return ResponseEntity.ok(ApiResponse.success(kpiId.toString()));
    }

    //KPI 수정
    @PatchMapping("/{kpiId}")
    public ResponseEntity<ApiResponse<Void>> updateKpi(
            @PathVariable Long goalId,
            @PathVariable Long kpiId,
            @RequestBody UpdateKpiRequest request
    ) {
        kpiCommandService.updateKpi(goalId, kpiId, request);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    //KPI 재등록
    @PostMapping("/{kpiId}/resubmit")
    public ResponseEntity<ApiResponse<String>> resubmitKpi(
            @PathVariable Long goalId,
            @PathVariable Long kpiId,
            @RequestBody ResubmitKpiRequest request
    ) {
        Long newKpiId = kpiCommandService.resubmitKpi(goalId, kpiId, request);
        return ResponseEntity.ok(ApiResponse.success(newKpiId.toString()));
    }
}
