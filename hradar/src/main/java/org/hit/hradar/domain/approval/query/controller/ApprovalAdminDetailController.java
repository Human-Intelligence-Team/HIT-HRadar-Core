package org.hit.hradar.domain.approval.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.approval.query.dto.response.ApprovalDetailResponse;
import org.hit.hradar.domain.approval.query.mapper.ApprovalDetailQueryMapper;
import org.hit.hradar.global.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Approval Admin Query", description = "관리자용 결재 조회 API")
@RestController
@RequestMapping("/approvals/admin")
@RequiredArgsConstructor
public class ApprovalAdminDetailController {

  private final ApprovalDetailQueryMapper approvalDetailQueryMapper;

  @Operation(summary = "관리자용 결재 상세 조회", description = "관리자 권한으로 결재 문서 상세 정보를 조회합니다.")
  @GetMapping("/{docId}")
  public ResponseEntity<ApiResponse<ApprovalDetailResponse>> getDetail(
      @PathVariable Long docId) {
    return ResponseEntity.ok(ApiResponse.success(
        approvalDetailQueryMapper.selectApprovalDetailByAdmin(docId)));
  }
}
