package org.hit.hradar.domain.approval.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.approval.command.application.dto.request.ApprovalDocumentTypeRequest;
import org.hit.hradar.domain.approval.command.application.service.ApprovalDocumentTypeCommandService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Approval Document Type Command", description = "결재 문서 종류 관리 API")
@RestController
@RequestMapping("/approval-types")
@RequiredArgsConstructor
public class ApprovalDocumentTypeCommandController {

  private final ApprovalDocumentTypeCommandService approvalDocumentTypeCommandService;

  @Operation(summary = "결재 문서 종류 생성", description = "새로운 결재 문서 종류를 생성합니다.")
  @PostMapping
  public ResponseEntity<ApiResponse<Void>> create(
      @CurrentUser AuthUser authUser,
      @RequestBody ApprovalDocumentTypeRequest request) {
    approvalDocumentTypeCommandService.create(
        authUser.companyId(),
        request);
    return ResponseEntity.ok(ApiResponse.success(null));
  }

  @Operation(summary = "결재 문서 종류 수정", description = "기존 결재 문서 종류를 수정합니다.")
  @PutMapping("/{docId}")
  public ResponseEntity<ApiResponse<Void>> update(
      @PathVariable Long docId,
      @RequestBody ApprovalDocumentTypeRequest request) {
    approvalDocumentTypeCommandService.update(
        docId,
        request);
    return ResponseEntity.ok(ApiResponse.success(null));
  }

  @Operation(summary = "결재 문서 종류 삭제", description = "결재 문서 종류를 삭제합니다.")
  @DeleteMapping("/{docId}")
  public ResponseEntity<ApiResponse<Void>> delete(
      @PathVariable Long docId) {
    approvalDocumentTypeCommandService.delete(docId);
    return ResponseEntity.ok(ApiResponse.success(null));
  }

}
