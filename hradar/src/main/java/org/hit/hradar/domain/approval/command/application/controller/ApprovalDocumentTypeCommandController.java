package org.hit.hradar.domain.approval.command.application.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.approval.command.application.dto.request.ApprovalDocumentTypeRequest;
import org.hit.hradar.domain.approval.command.application.service.ApprovalDocumentTypeCommandService;
import org.hit.hradar.global.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/approval-types")
@RequiredArgsConstructor
public class ApprovalDocumentTypeCommandController {

  private final ApprovalDocumentTypeCommandService approvalDocumentTypeCommandService;

  @PostMapping
  public ResponseEntity<ApiResponse<Void>> create(
      @RequestBody ApprovalDocumentTypeRequest request
  ) {
    approvalDocumentTypeCommandService.create(
        request
    );
    return ResponseEntity.ok(ApiResponse.success(null));
  }

  @PutMapping("/{docId}")
  public ResponseEntity<ApiResponse<Void>> update(
      @PathVariable Long docId,
      @RequestBody ApprovalDocumentTypeRequest request
  ) {
    approvalDocumentTypeCommandService.update(
        docId,
        request
    );
    return ResponseEntity.ok(ApiResponse.success(null));
  }

  @DeleteMapping("/{docId}")
  public ResponseEntity<ApiResponse<Void>> delete(
      @PathVariable Long docId
  ) {
    approvalDocumentTypeCommandService.delete(docId);
    return ResponseEntity.ok(ApiResponse.success(null));
  }

}
