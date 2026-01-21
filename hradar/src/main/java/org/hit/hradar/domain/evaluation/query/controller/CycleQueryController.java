package org.hit.hradar.domain.evaluation.query.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.evaluation.query.dto.response.CycleDetailResponseDto;
import org.hit.hradar.domain.evaluation.query.dto.response.CycleListResponseDto;
import org.hit.hradar.domain.evaluation.query.service.CycleQueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cycles")
public class CycleQueryController {

    private final CycleQueryService cycleQueryService;

    @GetMapping
    public List<CycleListResponseDto> getCycleList() {
        return cycleQueryService.getCycleList();
    }

    @GetMapping("/{cycleId}")
    public CycleDetailResponseDto getCycleDetail(
            @PathVariable Long cycleId
    ) {
        return cycleQueryService.getCycleDetail(cycleId);
    }
}
