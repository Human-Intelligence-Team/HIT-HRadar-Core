package org.hit.hradar.domain.approval.query.controller;

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

@RestController
@RequiredArgsConstructor
@RequestMapping("/approvals")
public class ApprovalAdminQueryController {

  private final ApprovalAdminQueryService approvalAdminQueryService;

  @GetMapping("/all-document")
  public ResponseEntity<ApiResponse<List<ApprovalAdminDocumentResponse>>> getAllDocument(
    ApprovalAdminSearchRequest request
  ) {
    return ResponseEntity.ok(ApiResponse.success(
        approvalAdminQueryService.findAll(request)
    ));
  }
}
