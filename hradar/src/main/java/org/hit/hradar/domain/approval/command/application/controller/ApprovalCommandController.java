package org.hit.hradar.domain.approval.command.application.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.approval.command.application.dto.request.ApprovalCreateRequest;
import org.hit.hradar.domain.approval.command.application.service.ApprovalApproveCommandService;
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

  private final ApprovalApproveCommandService approvalCommandService;
  private final ApprovalWithdrawCommandService approvalWithdrawCommandService;
  private final ApprovalProviderService approvalProviderService;
  private final ApprovalRejectCommandService approvalRejectCommandService;

  //결재 문서 임시저장
  @PostMapping("/draft")
  public ResponseEntity<ApiResponse<Long>> saveDraft(
      @CurrentUser AuthUser authUser,
      @RequestBody ApprovalCreateRequest request
  ) {
    Long docId = approvalProviderService.save(
        null,               //최초 저장
        authUser.employeeId(),
        authUser.companyId(),
        request,
        ApprovalSaveMode.DRAFT
    );

    return ResponseEntity.ok(
        ApiResponse.success(docId));
  }

  //결재 상신 요청
  @PostMapping("/{docId}/submit")
  public ResponseEntity<ApiResponse<String>> submit(
      @PathVariable Long docId,
      @CurrentUser AuthUser authUser
  ) {
    approvalProviderService.save(
        docId,
        authUser.employeeId(),
        authUser.companyId(),
        null,
        ApprovalSaveMode.SUBMIT
    );
    return ResponseEntity.ok(ApiResponse.success("submitted"));
  }


  //결재 승인 처리
  @PostMapping("/{docId}/approve")
  public ResponseEntity<ApiResponse<String>> approve(
      @CurrentUser AuthUser authUser,
      @PathVariable Long docId
  ) {
    approvalCommandService.approve(
        docId,
        authUser.employeeId(),
        authUser.companyId()
    );

    // 성공 시 공통 응답 포맷으로 OK 반환
    return ResponseEntity.ok(
        ApiResponse.success("approved")
    );
  }

  // 결재 반려 처리
  @PostMapping("/{docId}/reject")
  public ResponseEntity<ApiResponse<String>> reject(
      @CurrentUser AuthUser authUser,
      @PathVariable Long docId,
      @RequestParam String reason
  ) {

    approvalRejectCommandService.reject(
        docId,
        authUser.employeeId(),
        reason
    );

    return ResponseEntity.ok(
        ApiResponse.success("rejected")
    );
  }

  // 결재 문서 회수 (기안자)
  @PostMapping("/{docId}/withdraw")
  public ResponseEntity<ApiResponse<String>> withdraw(
      @PathVariable Long docId,
      @CurrentUser AuthUser authUser
  ) {
    approvalWithdrawCommandService.withdraw(
        docId,
        authUser.employeeId()
    );
    return ResponseEntity.ok(ApiResponse.success("withdrawn"));
  }
}
