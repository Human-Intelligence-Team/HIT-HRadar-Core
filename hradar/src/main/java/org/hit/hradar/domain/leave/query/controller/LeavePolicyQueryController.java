package org.hit.hradar.domain.leave.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.leave.command.domain.aggregate.LeavePolicy;
import org.hit.hradar.domain.leave.query.service.LeavePolicyQueryService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Leave Policy Query", description = "회사 휴가 정책(종류, 지급 기준 등) 조회 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/leave-policies")
public class LeavePolicyQueryController {

  private final LeavePolicyQueryService leavePolicyQueryService;

  @Operation(summary = "전체 휴가 정책 조회", description = "회사가 정의한 모든 휴가 종류(연차, 경조사, 병가 등)와 각각의 발생/사용 규칙 목록을 조회합니다.")
  @GetMapping
  public ApiResponse<List<LeavePolicy>> getPolicies(
      @CurrentUser AuthUser authUser) {
    return ApiResponse.success(
        leavePolicyQueryService.getPolicies(authUser.companyId()));

  }
}