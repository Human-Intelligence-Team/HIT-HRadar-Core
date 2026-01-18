package org.hit.hradar.domain.approval.query.controller;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/approvals")
@RequiredArgsConstructor
public class ApprovalQueryController {

  private final ApprovalQueryService approvalQueryService;

  //내 문서함 조회
  //사원 기준(내가 기안하 문서)
  @GetMapping("/my-documents")
  public ApiResponse<List<ApprovalMyDocumentResponse>> myDocuments(
      @CurrentUser AuthUser authUser
  ) {
    return ApiResponse.success(
        approvalQueryService.findMyDocuments(authUser.userId())
    );
  }

  //결재 문서함 조회(팀장/인사팀 기준 내가 결재자(대리결재자) 문서)
  @GetMapping("/approval-tasks")
  public ApiResponse<List<ApprovalTaskResponse>> InboxDocuments(
      @CurrentUser AuthUser authUser
  ) {
    return ApiResponse.success(
        approvalQueryService.findApprovalTasks(
            authUser.userId(),
            authUser.role()
        )
    );
  }

  //반려 문서함 조회(내가 기안한 반려 문서)
  @GetMapping("/rejected-document")
  public ApiResponse<List<ApprovalRejectedDocumentResponse>> rejectedDocument(
      @CurrentUser AuthUser authUser
  ) {
        return ApiResponse.success(
            approvalQueryService.findRejectedDocuments(authUser.userId())
        );
  }

  @GetMapping("/references")
  public ApiResponse<List<ApprovalReferenceDocumentResponse>> reference(
      @CurrentUser AuthUser authUser
  ) {
    return ApiResponse.success(
        approvalQueryService.findReferenceDocuments(authUser.userId())
    );
  }
}
