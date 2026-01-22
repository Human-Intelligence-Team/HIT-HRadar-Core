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
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/company-applications")
public class ComAppCommandController {

  private final ComAppCommandService comAppCommandService;
  private final ComAppApprovalService comAppApprovalService;

  @PostMapping
  public ResponseEntity<CreateComAppResponse> create(@Valid @RequestBody CreateComAppRequest req) {
    return ResponseEntity.ok(comAppCommandService.create(req));
  }

  // 승인
  @PostMapping("/{applicationId}/approve")
  public ResponseEntity<ApproveComAppResponse> approve(
      @CurrentUser AuthUser authUser,
      @PathVariable Long applicationId
  ) {
    return ResponseEntity.ok(comAppApprovalService.approve(applicationId, authUser.userId()));
  }

  // 반려
  @PostMapping("/{applicationId}/reject")
  public ResponseEntity<RejectComAppResponse> reject(
      @CurrentUser AuthUser authUser,
      @PathVariable Long applicationId,
      @Valid @RequestBody RejectComAppRequest req
  ) {
    // 승인자(반려자) userId를 서비스로 전달 (권한 체크/리뷰어 기록에 사용)
    return ResponseEntity.ok(
        comAppCommandService.reject(applicationId, authUser.userId(), req.getReason())
    );
  }
}
