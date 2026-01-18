package org.hit.hradar.domain.approval.query.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.approval.query.dto.response.ApprovalDetailResponse;
import org.hit.hradar.domain.approval.query.service.ApprovalDetailQueryService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/approvals")
@RequiredArgsConstructor
public class ApprovalDetailQueryController {

  private final ApprovalDetailQueryService approvalDetailQueryService;

  @GetMapping("{docId}")
  public ResponseEntity<ApiResponse<ApprovalDetailResponse>> detail(
      @CurrentUser AuthUser authUser,
      @PathVariable Long docId
  ) {
    return ResponseEntity.ok(
        ApiResponse.success(
            approvalDetailQueryService.findDetail(docId, authUser.userId())
        )
    );
  }
}
