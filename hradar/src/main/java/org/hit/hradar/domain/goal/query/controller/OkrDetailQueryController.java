package org.hit.hradar.domain.goal.query.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.goal.query.dto.response.OkrDetailResponseDto;
import org.hit.hradar.domain.goal.query.service.OkrDetailQueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/key-results")
public class OkrDetailQueryController {

    private final OkrDetailQueryService okrDetailQueryService;

    @GetMapping("/{keyResultId}/detail")
    public OkrDetailResponseDto getKrDetail(
            @PathVariable Long keyResultId
    ) {
        return okrDetailQueryService.getKrDetail(keyResultId);
    }
}
