package org.hit.hradar.domain.goal.command.application.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.goal.command.application.dto.request.CreateOkrKeyResultRequest;
import org.hit.hradar.domain.goal.command.application.service.OkrCommandService;
import org.hit.hradar.global.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/goal/{goalId}/okr")
public class OkrCommandController {

    private final OkrCommandService okrCommandService;

    @PostMapping("/key-results")
    public ResponseEntity<ApiResponse<String>> createKeyResult(
            @PathVariable Long goalId,
            @RequestBody CreateOkrKeyResultRequest request
    ) {
        Long krId = okrCommandService.createKeyResult(goalId, request);
        return ResponseEntity.ok(ApiResponse.success(krId.toString()));
    }
}
