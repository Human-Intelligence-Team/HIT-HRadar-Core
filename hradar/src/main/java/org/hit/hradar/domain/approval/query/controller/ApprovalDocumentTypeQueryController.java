package org.hit.hradar.domain.approval.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalAttendanceCategory;
import org.hit.hradar.domain.approval.query.dto.response.ApprovalDocumentTypeResponse;
import org.hit.hradar.domain.approval.query.service.ApprovalDocumentTypeQueryService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Approval Document Type Query", description = "결재 문서 종류 조회 API")
@RestController
@RequestMapping("/approval-types")
@RequiredArgsConstructor
public class ApprovalDocumentTypeQueryController {

  private final ApprovalDocumentTypeQueryService approvalDocumentTypeQueryService;

  @Operation(summary = "활성 결재 문서 종류 목록 조회", description = "사용 가능한 모든 결재 문서 종류 목록을 조회합니다.")
  @GetMapping
  public ResponseEntity<ApiResponse<List<ApprovalDocumentTypeResponse>>> getAllActive(
      @CurrentUser AuthUser authUser) {
    return ResponseEntity.ok(
        ApiResponse.success(
            approvalDocumentTypeQueryService.findAllActiveTypes(authUser.companyId())));
  }

  @Operation(summary = "근태 연동 카테고리 목록 조회", description = "결재 문서 유형 등록 시 사용할 수 있는 근태 연동 카테고리 목록을 조회합니다.")
  @GetMapping("/categories")
  public ResponseEntity<ApiResponse<ApprovalAttendanceCategory[]>> getCategories() {
    return ResponseEntity.ok(
        ApiResponse.success(ApprovalAttendanceCategory.values()));
  }
}