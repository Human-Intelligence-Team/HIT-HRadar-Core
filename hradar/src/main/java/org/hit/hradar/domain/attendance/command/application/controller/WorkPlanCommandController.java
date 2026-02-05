package org.hit.hradar.domain.attendance.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.approval.command.application.dto.request.ApprovalDraftCreateRequest;
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

@Tag(name = "Work Plan Command", description = "근무 계획 관리 API")
@RestController
@RequestMapping("/work-plans")
@RequiredArgsConstructor
public class WorkPlanCommandController {

  private final WorkPlanCommandService workPlanCommandService;
  private final ApprovalProviderService approvalProviderService;

  @Operation(summary = "근무 계획 임시저장", description = "근무 계획 결재 문서를 임시저장 상태로 생성합니다.")
  @PostMapping("/draft")
  public Long createDraft(
      @CurrentUser AuthUser authUser,
      @RequestBody ApprovalDraftCreateRequest request) {
    return approvalProviderService.save(
        null,
        authUser.employeeId(),
        authUser.companyId(),
        request,
        ApprovalSaveMode.DRAFT);
  }

  @Operation(summary = "근무 계획 상신", description = "근무 계획을 생성하고 결재를 상신합니다.")
  @PostMapping("/{docId}/submit")
  public void submitWorkPlan(
      @PathVariable Long docId,
      @CurrentUser AuthUser authUser,
      @RequestBody WorkPlanCreateRequest request) {
    workPlanCommandService.createWorkPlan(
        authUser.employeeId(),
        docId,
        request);

    approvalProviderService.save(
        docId,
        authUser.employeeId(),
        authUser.companyId(),
        null,
        ApprovalSaveMode.SUBMIT);
  }
}
