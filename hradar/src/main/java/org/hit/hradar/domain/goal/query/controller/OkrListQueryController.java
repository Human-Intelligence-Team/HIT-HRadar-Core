package org.hit.hradar.domain.goal.query.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.goal.query.dto.response.OkrListResponseDto;
import org.hit.hradar.domain.goal.query.service.OkrListQueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/goals")
public class OkrListQueryController {

    private final OkrListQueryService okrListQueryService;

    @GetMapping("/{goalId}/okrs")
    public List<OkrListResponseDto> getGoalOkrs(
            @PathVariable Long goalId
    ) {
        return okrListQueryService.getOkrs(goalId);
    }
}
