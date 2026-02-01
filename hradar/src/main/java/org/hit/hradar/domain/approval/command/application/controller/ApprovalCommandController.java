package org.hit.hradar.domain.approval.command.application.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.approval.command.application.dto.request.ApprovalCommentCreateRequest;
import org.hit.hradar.domain.approval.command.application.dto.request.ApprovalDraftCreateRequest;
import org.hit.hradar.domain.approval.command.application.service.ApprovalApproveCommandService;
import org.hit.hradar.domain.approval.command.application.service.ApprovalCommentCommandService;
import org.hit.hradar.domain.approval.command.application.service.ApprovalRejectCommandService;
import org.hit.hradar.domain.approval.command.application.service.ApprovalWithdrawCommandService;
import org.hit.hradar.domain.approval.command.application.service.provider.ApprovalProviderService;
import org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalSaveMode;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/approvals")
@RequiredArgsConstructor
public class ApprovalCommandController {

  private final ApprovalProviderService approvalProviderService;
  private final ApprovalApproveCommandService approvalApproveCommandService;
  private final ApprovalRejectCommandService approvalRejectCommandService;
  private final ApprovalWithdrawCommandService approvalWithdrawCommandService;
  private final ApprovalCommentCommandService approvalCommentCommandService;

  //임시저장
  @PostMapping("/draft")
  public ResponseEntity<ApiResponse<Long>> draft(
      @CurrentUser AuthUser authUser,
      @RequestBody ApprovalDraftCreateRequest request
  ) {
    Long docId = approvalProviderService.save(
        null,
        authUser.employeeId(),
        authUser.companyId(),
        request,
        ApprovalSaveMode.DRAFT
    );
    return ResponseEntity.ok(
        ApiResponse.success(docId)
    );
  }

  //바로 상신 (신규 생성 + 상신)
  @PostMapping("/submit")
  public ResponseEntity<ApiResponse<Long>> submitNew(
      @CurrentUser AuthUser authUser,
      @RequestBody ApprovalDraftCreateRequest request
  ) {
    Long docId = approvalProviderService.save(
        null,
        authUser.employeeId(),
        authUser.companyId(),
        request,
        ApprovalSaveMode.SUBMIT
    );
    return ResponseEntity.ok(
        ApiResponse.success(docId)
    );
  }

  //상신
  @PostMapping("/{docId}/submit")
  public ResponseEntity<ApiResponse<Void>> submit(
      @CurrentUser AuthUser authUser,
      @PathVariable Long docId
  ) {
    approvalApproveCommandService.approve(
        docId,
        authUser.employeeId(),
        authUser.userId(),
        authUser.companyId()
    );
    return ResponseEntity.ok(
        ApiResponse.success(null)
    );
  }

  //승인
  @PostMapping("/{docId}/approve")
  public ResponseEntity<ApiResponse<Void>> approve(
      @CurrentUser AuthUser authUser,
      @PathVariable Long docId
  ) {
    approvalApproveCommandService.approve(
        docId,
        authUser.employeeId(),
        authUser.userId(),
        authUser.companyId()
    );
    return ResponseEntity.ok(
        ApiResponse.success(null)
    );
  }

  //반려
  @PostMapping("/{docId}/reject")
  public ResponseEntity<ApiResponse<Void>> reject(
      @CurrentUser AuthUser authUser,
      @PathVariable Long docId,
      @RequestParam String reason
  ) {
    approvalRejectCommandService.reject(
        docId,
        authUser.employeeId(), // History/Log (EmpID)
        authUser.userId(),     // Permission/Step Match (AccountID)
        reason
    );
    return ResponseEntity.ok(
        ApiResponse.success(null)
    );
  }

  //회수
  @PostMapping("/{docId}/withdraw")
  public ResponseEntity<ApiResponse<Void>> withdraw(
      @PathVariable Long docId,
      @CurrentUser AuthUser authUser
  ) {
    approvalWithdrawCommandService.withdraw(docId, authUser.employeeId());
    return ResponseEntity.ok(ApiResponse.success(null));
  }

  @PostMapping("/{docId}/comments")
  public ResponseEntity<ApiResponse<Void>> addComment(
      @PathVariable Long docId,
      @CurrentUser AuthUser authUser,
      @RequestBody ApprovalCommentCreateRequest request
  ) {
    approvalCommentCommandService.addComment(
        docId,
        authUser.employeeId(),
        request.getContent(),
        request.getParentCommentId(),
        authUser.userId()
    );
    return ResponseEntity.ok(ApiResponse.success(null));
  }



}