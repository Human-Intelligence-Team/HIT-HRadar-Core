package org.hit.hradar.domain.evaluation.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.evaluation.command.application.dto.request.CycleCreateRequestDto;
import org.hit.hradar.domain.evaluation.command.application.dto.request.CycleUpdateRequestDto;
import org.hit.hradar.domain.evaluation.command.application.service.CycleCommandService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Evaluation Cycle Command", description = "평가 주기(회차) 관리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/evaluation-cycles")
public class CycleCommandController {

    private final CycleCommandService cycleCommandService;

    /* 회차 생성 */
    @Operation(summary = "평가 주기 생성", description = "새로운 평가 주기(회차)를 생성합니다.")
    @PostMapping()
    public ResponseEntity<ApiResponse<String>> createCycle(
            @CurrentUser AuthUser authUser,
            @RequestBody CycleCreateRequestDto request) {
        cycleCommandService.createCycle(authUser.companyId(), request, authUser.employeeId());
        return ResponseEntity.ok(ApiResponse.success("null"));
    }

    /* 회차 수정 */
    @Operation(summary = "평가 주기 수정", description = "평가 주기의 기간 정보를 수정합니다.")
    @PutMapping("/{cycleId}")
    public ResponseEntity<ApiResponse<String>> updateCycle(
            @PathVariable Long cycleId,
            @RequestBody CycleUpdateRequestDto request) {
        cycleCommandService.updatePeriod(cycleId, request);
        return ResponseEntity.ok(ApiResponse.success("null"));
    }

    /* 강제 마감 */
    @Operation(summary = "평가 주기 강제 마감", description = "진행 중인 평가 주기를 강제로 마감합니다.")
    @PostMapping("/{cycleId}/close")
    public ResponseEntity<ApiResponse<String>> forceClose(
            @PathVariable Long cycleId) {
        cycleCommandService.forceClose(cycleId);
        return ResponseEntity.ok(ApiResponse.success("null"));
    }

    /* 회차 삭제 */
    @Operation(summary = "평가 주기 삭제", description = "평가 주기를 삭제합니다.")
    @DeleteMapping("/{cycleId}/delete")
    public ResponseEntity<ApiResponse<String>> deleteCycle(
            @PathVariable Long cycleId) {
        cycleCommandService.deleteCycle(cycleId);
        return ResponseEntity.ok(ApiResponse.success("null"));
    }

    /* 회차 승인 */
    @Operation(summary = "평가 주기 승인", description = "생성된 평가 주기를 최종 승인하여 진행 가능 상태로 변경합니다.")
    @PutMapping("/{cycleId}/approve")
    public ResponseEntity<ApiResponse<String>> approveCycle(
            @PathVariable Long cycleId) {
        cycleCommandService.approveCycle(cycleId);
        return ResponseEntity.ok(ApiResponse.success("null"));
    }

}
