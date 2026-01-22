package org.hit.hradar.domain.evaluation.command.application.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.evaluation.command.application.dto.request.CycleCreateRequestDto;
import org.hit.hradar.domain.evaluation.command.application.dto.request.CycleUpdateRequestDto;
import org.hit.hradar.domain.evaluation.command.application.service.CycleCommandService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/evaluation-cycles")
public class CycleCommandController {

    private final CycleCommandService cycleCommandService;

    /* 회차 생성 */
    @PostMapping("/{companyId}")
    public ResponseEntity<ApiResponse<String>> createCycle(
            @CurrentUser AuthUser authUser,
            @PathVariable Long companyId,
            @RequestBody CycleCreateRequestDto request
    ) {
        cycleCommandService.createCycle(companyId, request,  authUser.userId());
        return ResponseEntity.ok(ApiResponse.success("null"));
    }

    /* 회차 수정*/
    @PutMapping("/{cycleId}")
    public ResponseEntity<ApiResponse<String>> updateCycle(
            @PathVariable Long cycleId,
            @RequestBody CycleUpdateRequestDto request
    ) {
        cycleCommandService.updatePeriod(cycleId, request);
        return ResponseEntity.ok(ApiResponse.success("null"));
    }

    /* 강제 마감 */
    @PostMapping("/{cycleId}/close")
    public ResponseEntity<ApiResponse<String>> forceClose(
            @PathVariable Long cycleId
    ) {
        cycleCommandService.forceClose(cycleId);
        return ResponseEntity.ok(ApiResponse.success("null"));
    }

    /*회차 삭제*/
    @DeleteMapping("/{cycleId}/delete")
    public ResponseEntity<ApiResponse<String>> deleteCycle(
            @PathVariable Long cycleId
    ){
        cycleCommandService.deleteCycle(cycleId);
        return ResponseEntity.ok(ApiResponse.success("null"));
    }

    /*회차 승인*/
    @PutMapping("/{cycleId}/approve")
    public ResponseEntity<ApiResponse<String>> approveCycle(
            @PathVariable Long cycleId
    ){
        cycleCommandService.approveCycle(cycleId);
        return ResponseEntity.ok(ApiResponse.success("null"));
    }

}
