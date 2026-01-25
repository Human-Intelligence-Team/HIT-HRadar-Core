package org.hit.hradar.domain.leave.command.application.controller;

import lombok.RequiredArgsConstructor;

import org.hit.hradar.domain.approval.command.application.dto.request.ApprovalCreateRequest;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/leave")
public class LeaveCommandController {

  private final LeaveCommandService leaveCommandService;
  private final ApprovalProviderService approvalProviderService;

  //휴가 결재 신청(docid반환 draft생성)
  @PostMapping("/draft")
  public ResponseEntity<ApiResponse<String>> createLeaveDraft(
      @CurrentUser AuthUser authUser,
      @RequestBody ApprovalCreateRequest request
  ) {
    Long docId = approvalProviderService.save(
        null,
        authUser.employeeId(),
        authUser.companyId(),
        request,
        ApprovalSaveMode.DRAFT
    );

    return ResponseEntity.ok(
        ApiResponse.success("Draft"));
  }


  //휴가 내용저장
  @PostMapping("/{docId}")
  public ResponseEntity<ApiResponse<String>> applyLeave(
      @CurrentUser AuthUser authUser,
      @PathVariable Long docId,
      @RequestBody LeaveApplyRequest request
  ) {
    leaveCommandService.applyLeave(
        docId,
        authUser.companyId(),
        request
    );
    return ResponseEntity.ok(
        ApiResponse.success("leave_saved")
    );
  }
}
