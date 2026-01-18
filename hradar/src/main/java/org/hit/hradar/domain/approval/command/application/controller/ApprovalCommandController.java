package org.hit.hradar.domain.approval.command.application.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.approval.command.application.dto.request.ApprovalApproveRequest;
import org.hit.hradar.domain.approval.command.application.dto.request.ApprovalRejectRequest;
import org.hit.hradar.domain.approval.command.application.service.ApprovalCommandService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/approvals")
@RequiredArgsConstructor
public class ApprovalCommandController {

  private final ApprovalCommandService approvalCommandService;

  //결재 승인 처리
  @PostMapping("/approve")
  public ResponseEntity<ApiResponse<String>> approve(
      @CurrentUser AuthUser authUser,
      @RequestBody ApprovalApproveRequest request
  ) {
    approvalCommandService.approve(
        request.getDocId(),
        authUser.userId()
    );
    // 성공 시 공통 응답 포맷으로 OK 반환
    return ResponseEntity.ok(
        ApiResponse.success("approved")
    );
  }

  //결재 반려 처리
  @PostMapping("/reject")
  public ResponseEntity<ApiResponse<String>> reject(
      @CurrentUser AuthUser authUser,
      @RequestBody ApprovalRejectRequest request
  ) {

    approvalCommandService.reject(
        request.getDocId(),
        authUser.userId(),
        request.getReason()
    );

    return ResponseEntity.ok(
        ApiResponse.success("rejected")
    );
  }
}
