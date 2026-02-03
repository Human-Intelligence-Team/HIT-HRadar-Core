package org.hit.hradar.domain.goal.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.goal.query.dto.response.OkrListResponseDto;
import org.hit.hradar.domain.goal.query.service.OkrListQueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Goal OKR List Query", description = "목표별 OKR(Key Results) 목록 조회 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/goals")
public class OkrListQueryController {

    private final OkrListQueryService okrListQueryService;

    @Operation(summary = "목표별 OKR 목록 조회", description = "특정 목표에 연결된 모든 핵심 결과(Key Results) 목록을 조회합니다.")
    @GetMapping("/{goalId}/okrs")
    public List<OkrListResponseDto> getGoalOkrs(
            @PathVariable Long goalId) {
        return okrListQueryService.getOkrs(goalId);
    }
}
