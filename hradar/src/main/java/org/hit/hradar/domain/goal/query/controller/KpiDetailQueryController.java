package org.hit.hradar.domain.goal.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.goal.query.dto.response.KpiDetailResponseDto;
import org.hit.hradar.domain.goal.query.service.KpiDetailQueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Goal KPI Detail Query", description = "KPI 상세 정보 및 로그 조회 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/kpis")
public class KpiDetailQueryController {

    private final KpiDetailQueryService kpiDetailQueryService;

    /* KPI 상세 및 로그 조회 */
    @Operation(summary = "KPI 상세 조회", description = "특정 KPI의 상세 정보와 수치 변경 로그를 조회합니다.")
    @GetMapping("/{kpiId}/detail")
    public KpiDetailResponseDto getKpiDetail(
            @PathVariable Long kpiId) {
        return kpiDetailQueryService.getKpiDetail(kpiId);
    }
}
