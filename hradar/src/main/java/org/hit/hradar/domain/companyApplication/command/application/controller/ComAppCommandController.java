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
      @RequestBody @Valid CreateComAppRequest request
  ) {
    CreateComAppResponse response = comAppCommandService.create(request);
    return ResponseEntity.ok(ApiResponse.success(response));
  }

  @PostMapping("/{applicationId}/approve")
  public ResponseEntity<ApiResponse<ApproveComAppResponse>> approve(
      @PathVariable Long applicationId,
      @CurrentUser AuthUser authUser
  ) {
    Long userId = authUser.userId();
    String role = authUser.role();

    ApproveComAppResponse response =
        comAppApprovalService.approve(applicationId, userId, role);

    return ResponseEntity.ok(ApiResponse.success(response));
  }

  @PostMapping("/{applicationId}/reject")
  public ResponseEntity<ApiResponse<RejectComAppResponse>> reject(
      @PathVariable Long applicationId,
      @RequestBody @Valid RejectComAppRequest request,
      @CurrentUser AuthUser authUser
  ) {
    Long userId = authUser.userId();
    String role = authUser.role();

    RejectComAppResponse response =
        comAppApprovalService.reject(applicationId, userId, role, request.getReason());

    return ResponseEntity.ok(ApiResponse.success(response));
  }
}
