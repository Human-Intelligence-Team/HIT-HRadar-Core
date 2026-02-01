package org.hit.hradar.domain.approval.query.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.approval.query.dto.response.ApprovalDocumentTypeResponse;
import org.hit.hradar.domain.approval.query.service.ApprovalDocumentTypeQueryService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/approval-types")
@RequiredArgsConstructor
public class ApprovalDocumentTypeQueryController {

  private final ApprovalDocumentTypeQueryService approvalDocumentTypeQueryService;

  @GetMapping
  public ResponseEntity<ApiResponse<List<ApprovalDocumentTypeResponse>>> getAllActive(
      @CurrentUser AuthUser authUser
  ) {
    return ResponseEntity.ok(
        ApiResponse.success(
            approvalDocumentTypeQueryService.findAllActiveTypes(authUser.companyId())
        )
    );
  }
}