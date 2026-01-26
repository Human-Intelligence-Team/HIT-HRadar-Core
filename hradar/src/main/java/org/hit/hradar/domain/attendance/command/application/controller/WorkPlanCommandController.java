package org.hit.hradar.domain.attendance.command.application.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.approval.command.application.dto.request.ApprovalCreateRequest;
import org.hit.hradar.domain.approval.command.application.service.provider.ApprovalProviderService;
import org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalSaveMode;
import org.hit.hradar.domain.attendance.command.application.dto.request.WorkPlanCreateRequest;
import org.hit.hradar.domain.attendance.command.application.service.WorkPlanCommandService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/work-plans")
@RequiredArgsConstructor
public class WorkPlanCommandController {

  private final WorkPlanCommandService workPlanCommandService;
  private final ApprovalProviderService approvalProviderService;

  @PostMapping("/draft")
  public Long createDraft(
      @CurrentUser AuthUser authUser,
      @RequestBody ApprovalCreateRequest request
  ) {
    return approvalProviderService.save(
        null,
        authUser.userId(),
        authUser.companyId(),
        request,
        ApprovalSaveMode.DRAFT
    );
  }

  @PostMapping("/{docId}/submit")
  public void submitWorkPlan(
      @PathVariable Long docId,
      @CurrentUser AuthUser authUser,
      @RequestBody WorkPlanCreateRequest request
  ) {
    workPlanCommandService.createWorkPlan(
        authUser.userId(),
        docId,
        request
    );

    approvalProviderService.save(
        docId,
        authUser.userId(),
        authUser.companyId(),
        null,
        ApprovalSaveMode.SUBMIT
    );
  }
}
