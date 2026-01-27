package org.hit.hradar.domain.approval.query.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.approval.query.dto.response.ApprovalDetailResponse;
import org.hit.hradar.domain.approval.query.mapper.ApprovalDetailQueryMapper;
import org.hit.hradar.global.dto.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/approvals/admin")
@RequiredArgsConstructor
public class ApprovalAdminDetailController {

  private final ApprovalDetailQueryMapper approvalDetailQueryMapper;

  @GetMapping("/{docId}")
  public ApiResponse<ApprovalDetailResponse> getDetail(
      @PathVariable Long docId
  ) {
    return ApiResponse.success(
        approvalDetailQueryMapper.selectApprovalDetailByAdmin(docId)
    );
  }
}
