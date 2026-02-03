package org.hit.hradar.domain.approval.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.approval.query.dto.request.ApprovalAdminSearchRequest;
import org.hit.hradar.domain.approval.query.dto.response.ApprovalAdminDocumentResponse;
import org.hit.hradar.domain.approval.query.service.ApprovalAdminQueryService;
import org.hit.hradar.global.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Approval Admin Query", description = "관리자용 결재 목록 조회 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/approvals")
public class ApprovalAdminQueryController {

  private final ApprovalAdminQueryService approvalAdminQueryService;

  @Operation(summary = "전체 결재 문서 조회", description = "관리자 권한으로 모든 결재 문서를 조회합니다.")
  @GetMapping("/all-document")
  public ResponseEntity<ApiResponse<List<ApprovalAdminDocumentResponse>>> getAllDocument(
      ApprovalAdminSearchRequest request) {
    return ResponseEntity.ok(ApiResponse.success(
        approvalAdminQueryService.findAll(request)));
  }
}
