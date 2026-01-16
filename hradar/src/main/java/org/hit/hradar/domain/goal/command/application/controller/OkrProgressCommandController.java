package org.hit.hradar.domain.goal.command.application.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.goal.command.application.dto.request.CreateOkrProgressRequest;
import org.hit.hradar.domain.goal.command.application.service.OkrProgressCommandService;
import org.hit.hradar.global.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/goals/{goalId}/key-results")
public class OkrProgressCommandController {

    private final OkrProgressCommandService okrProgressCommandService;

    @PostMapping("/{keyResultId}/progress")
    public ResponseEntity<ApiResponse<Void>> createProgress(
            @PathVariable Long keyResultId,
            @RequestHeader Long actorId,
            @RequestBody CreateOkrProgressRequest request
    ) {
        okrProgressCommandService.createProgress(keyResultId, actorId, request);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}

