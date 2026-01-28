package org.hit.hradar.domain.companyApplication.command.application.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.companyApplication.command.application.dto.ApproveComAppResponse;
import org.hit.hradar.domain.companyApplication.command.application.dto.CreateComAppRequest;
import org.hit.hradar.domain.companyApplication.command.application.dto.CreateComAppResponse;
import org.hit.hradar.domain.companyApplication.command.application.dto.RejectComAppRequest;
import org.hit.hradar.domain.companyApplication.command.application.dto.RejectComAppResponse;
import org.hit.hradar.domain.companyApplication.command.application.service.ComAppApprovalService;
import org.hit.hradar.domain.companyApplication.command.application.service.ComAppCommandService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/company-applications")
public class ComAppCommandController {

  private final ComAppCommandService comAppCommandService;
  private final ComAppApprovalService comAppApprovalService;

  /**
   * 회사 신청(제출)
   */
  @PostMapping("/submit")
  public ResponseEntity<ApiResponse<CreateComAppResponse>> create(
      @Valid @RequestBody CreateComAppRequest request
  ) {
    CreateComAppResponse response = comAppCommandService.create(request);
    return ResponseEntity.ok(ApiResponse.success(response));
  }

  /**
   * 회사 신청 승인
   */
  @PostMapping("/{applicationId}/approve")
  public ResponseEntity<ApiResponse<ApproveComAppResponse>> approve(
      @CurrentUser AuthUser authUser,
      @PathVariable Long applicationId
  ) {
    ApproveComAppResponse response = comAppApprovalService.approve(applicationId, authUser);
    return ResponseEntity.ok(ApiResponse.success(response));
  }

  /**
   * 회사 신청 반려
   */
  @PostMapping("/{applicationId}/reject")
  public ResponseEntity<ApiResponse<RejectComAppResponse>> reject(
      @CurrentUser AuthUser authUser,
      @PathVariable Long applicationId,
      @Valid @RequestBody RejectComAppRequest request
  ) {
    RejectComAppResponse response =
        comAppApprovalService.reject(applicationId, authUser, request.getReason());
    return ResponseEntity.ok(ApiResponse.success(response));
  }
}
