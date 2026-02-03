package org.hit.hradar.domain.approval.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.approval.query.dto.response.ApprovalMyDocumentResponse;
import org.hit.hradar.domain.approval.query.dto.response.ApprovalReferenceDocumentResponse;
import org.hit.hradar.domain.approval.query.dto.response.ApprovalRejectedDocumentResponse;
import org.hit.hradar.domain.approval.query.dto.response.ApprovalTaskResponse;
import org.hit.hradar.domain.approval.query.service.ApprovalQueryService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Approval Query", description = "결재 문서 목록 조회 API")
@RestController
@RequestMapping("/approvals")
@RequiredArgsConstructor
public class ApprovalQueryController {

    private final ApprovalQueryService approvalQueryService;

    // 내 문서함 조회
    // 사원 기준(내가 기안하 문서)
    @Operation(summary = "내 문서함 조회", description = "내가 기안한 결재 문서 목록을 조회합니다.")
    @GetMapping("/my-documents")
    public ResponseEntity<ApiResponse<List<ApprovalMyDocumentResponse>>> myDocuments(
            @CurrentUser AuthUser authUser) {
        return ResponseEntity.ok(ApiResponse.success(
                approvalQueryService.findMyDocuments(authUser.employeeId())));
    }

    // 결재 문서함 조회(팀장/인사팀 기준 내가 결재자(대리결재자) 문서)
    @Operation(summary = "결재 대기 문서함 조회", description = "내가 결재해야 할 문서 목록을 조회합니다.")
    @GetMapping("/approval-tasks")
    public ResponseEntity<ApiResponse<List<ApprovalTaskResponse>>> InboxDocuments(
            @CurrentUser AuthUser authUser) {
        return ResponseEntity.ok(ApiResponse.success(
                approvalQueryService.findApprovalTasks(
                        authUser.userId())));
    }

    // 반려 문서함 조회(내가 기안한 반려 문서)
    @Operation(summary = "반려 문서함 조회", description = "내가 기안한 결재 문서 중 반려된 목록을 조회합니다.")
    @GetMapping("/rejected-documents")
    public ResponseEntity<ApiResponse<List<ApprovalRejectedDocumentResponse>>> rejectedDocument(
            @CurrentUser AuthUser authUser) {
        return ResponseEntity.ok(ApiResponse.success(
                approvalQueryService.findRejectedDocuments(authUser.employeeId())));
    }

    // 참조 문서함
    @Operation(summary = "참조 문서함 조회", description = "내가 참조된 결재 문서 목록을 조회합니다.")
    @GetMapping("/references")
    public ResponseEntity<ApiResponse<List<ApprovalReferenceDocumentResponse>>> reference(
            @CurrentUser AuthUser authUser) {
        return ResponseEntity.ok(ApiResponse.success(
                approvalQueryService.findReferenceDocuments(authUser.userId())));
    }
}
