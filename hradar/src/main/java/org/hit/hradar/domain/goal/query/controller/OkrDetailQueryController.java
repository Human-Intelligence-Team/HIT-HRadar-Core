package org.hit.hradar.domain.goal.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.goal.query.dto.response.OkrDetailResponseDto;
import org.hit.hradar.domain.goal.query.service.OkrDetailQueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Goal OKR Detail Query", description = "핵심 결과(Key Result) 상세 정보 조회 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/key-results")
public class OkrDetailQueryController {

    private final OkrDetailQueryService okrDetailQueryService;

    @Operation(summary = "OKR 상세 조회", description = "특정 핵심 결과(Key Result)의 상세 내용과 진행 상황을 조회합니다.")
    @GetMapping("/{keyResultId}/detail")
    public OkrDetailResponseDto getKrDetail(
            @PathVariable Long keyResultId) {
        return okrDetailQueryService.getKrDetail(keyResultId);
    }
}
