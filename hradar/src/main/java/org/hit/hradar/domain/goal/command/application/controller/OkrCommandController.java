package org.hit.hradar.domain.goal.command.application.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.goal.command.application.dto.request.CreateOkrKeyResultRequest;
import org.hit.hradar.domain.goal.command.application.dto.request.ResubmitKeyResultRequest;
import org.hit.hradar.domain.goal.command.application.dto.request.UpdateKeyResultRequest;
import org.hit.hradar.domain.goal.command.application.service.OkrCommandService;
import org.hit.hradar.global.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/goals/{goalId}/key-results")
public class OkrCommandController {

    private final OkrCommandService okrCommandService;

    @PostMapping()
    public ResponseEntity<ApiResponse<String>> createKeyResult(
            @PathVariable Long goalId,
            @RequestBody CreateOkrKeyResultRequest request
    ) {
        Long krId = okrCommandService.createKeyResult(goalId, request);
        return ResponseEntity.ok(ApiResponse.success(krId.toString()));
    }

    // KR 수정
    @PatchMapping("/{keyResultId}")
    public ResponseEntity<ApiResponse<Void>> updateKeyResult(
            @PathVariable Long goalId,
            @PathVariable Long keyResultId,
            @RequestBody UpdateKeyResultRequest request
    ) {
        okrCommandService.updateKeyResult(goalId, keyResultId, request);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    // KR 재등록
    @PostMapping("/{keyResultId}/resubmit")
    public ResponseEntity<ApiResponse<String>> resubmitKeyResult(
            @PathVariable Long goalId,
            @PathVariable Long keyResultId,
            @RequestBody ResubmitKeyResultRequest request
    ) {
        Long newId = okrCommandService.resubmitKeyResult(goalId, keyResultId, request);
        return ResponseEntity.ok(ApiResponse.success(newId.toString()));
    }
}
