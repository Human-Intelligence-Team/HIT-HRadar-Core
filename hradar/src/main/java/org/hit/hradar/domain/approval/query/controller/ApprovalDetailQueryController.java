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

  //결재 문서 상세 조회(제목,본문,유형,상태)
  //결재선(단계,결재자,상태)
  //결재 히스토리(승인/반려/회수)
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
