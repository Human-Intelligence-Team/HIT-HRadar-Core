package org.hit.hradar.domain.evaluation.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.evaluation.query.dto.response.CycleDetailResponseDto;
import org.hit.hradar.domain.evaluation.query.dto.response.CycleListResponseDto;
import org.hit.hradar.domain.evaluation.query.service.CycleQueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Evaluation Cycle Query", description = "평가 주기 조회 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/evaluation-cycles")
public class CycleQueryController {

    private final CycleQueryService cycleQueryService;

    @Operation(summary = "평가 주기 목록 조회", description = "모든 평가 주기(회차) 목록을 조회합니다.")
    @GetMapping
    public List<CycleListResponseDto> getCycleList() {
        return cycleQueryService.getCycleList();
    }

    @Operation(summary = "평가 주기 상세 조회", description = "특정 평가 주기의 상세 정보를 조회합니다.")
    @GetMapping("/{cycleId}")
    public CycleDetailResponseDto getCycleDetail(
            @PathVariable Long cycleId) {
        return cycleQueryService.getCycleDetail(cycleId);
    }
}
