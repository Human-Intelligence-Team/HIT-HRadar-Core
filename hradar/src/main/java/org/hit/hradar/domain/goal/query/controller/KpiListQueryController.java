package org.hit.hradar.domain.goal.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.goal.query.dto.response.KpiListResponseDto;
import org.hit.hradar.domain.goal.query.service.KpiListQueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Goal KPI List Query", description = "목표별 KPI 목록 조회 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/goals")
public class KpiListQueryController {

    private final KpiListQueryService goalKpiListQueryService;

    @Operation(summary = "목표별 KPI 목록 조회", description = "특정 목표에 설정된 KPI(핵심 성과 지표) 목록을 조회합니다.")
    @GetMapping("/{goalId}/kpis")
    public List<KpiListResponseDto> getGoalKpis(
            @PathVariable Long goalId) {
        return goalKpiListQueryService.getKpis(goalId);
    }
}
