package org.hit.hradar.domain.leave.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.hit.hradar.domain.approval.command.application.dto.request.ApprovalDraftCreateRequest;
import org.hit.hradar.domain.approval.command.application.service.provider.ApprovalProviderService;

import org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalSaveMode;
import org.hit.hradar.domain.leave.command.application.dto.request.LeaveApplyRequest;
import org.hit.hradar.domain.leave.command.application.service.LeaveCommandService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Leave Command", description = "휴가 결재 상신 및 데이터 저장 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/leave/command")
public class LeaveCommandController {

    private final LeaveCommandService leaveCommandService;
    private final ApprovalProviderService approvalProviderService;

    // 휴가 결재 신청(docid반환 draft생성)
    @Operation(summary = "휴가 결재 기안 생성", description = "휴가 신청을 위한 전자결재 문서를 기안(Draft) 상태로 생성하고 문서 ID를 반환합니다.")
    @PostMapping("/draft")
    public ResponseEntity<ApiResponse<String>> createLeaveDraft(
            @CurrentUser AuthUser authUser,
            @RequestBody ApprovalDraftCreateRequest request) {
        Long docId = approvalProviderService.save(
                null,
                authUser.employeeId(),
                authUser.companyId(),
                request,
                ApprovalSaveMode.DRAFT);

        return ResponseEntity.ok(
                ApiResponse.success(docId.toString()));
    }

    // 휴가 내용저장
    @Operation(summary = "휴가 상세 내역 저장", description = "기안된 결재 문서에 구체적인 휴가 일시와 신청 내용을 연결하여 저장합니다.")
    @PostMapping("/{docId}")
    public ResponseEntity<ApiResponse<String>> applyLeave(
            @CurrentUser AuthUser authUser,
            @PathVariable Long docId,
            @RequestBody LeaveApplyRequest request) {
        leaveCommandService.applyLeave(
                docId,
                authUser.employeeId(),
                request);
        return ResponseEntity.ok(
                ApiResponse.success("leave_saved"));
    }
}
