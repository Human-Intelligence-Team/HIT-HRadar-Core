package org.hit.hradar.domain.goal.query.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.goal.query.dto.response.KpiDetailResponseDto;
import org.hit.hradar.domain.goal.query.service.KpiDetailQueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/kpis")
public class KpiDetailQueryController {

    private final KpiDetailQueryService kpiDetailQueryService;

    /*KPI 상세 및 로그 조회*/
    @GetMapping("/{kpiId}/detail")
    public KpiDetailResponseDto getKpiDetail(
            @PathVariable Long kpiId
    ){
        return kpiDetailQueryService.getKpiDetail(kpiId);
    }
}
