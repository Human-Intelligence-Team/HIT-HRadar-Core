package org.hit.hradar.domain.goal.command.application.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.goal.command.application.dto.request.CreateKpiRequest;
import org.hit.hradar.domain.goal.command.application.service.KpiCommandService;
import org.hit.hradar.global.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/kpis")
public class KpiCommandController {

    private final KpiCommandService kpiCommandService;

    @PostMapping
    public ResponseEntity<ApiResponse<String>> createKpi(
            @RequestBody CreateKpiRequest request
    ){
        Long kpiId = kpiCommandService.createKpi(request);
        return ResponseEntity.ok(ApiResponse.success(kpiId.toString()));
    }
}
