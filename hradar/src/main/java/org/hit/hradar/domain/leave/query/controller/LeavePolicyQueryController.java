package org.hit.hradar.domain.leave.query.controller;

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

@RestController
@RequiredArgsConstructor
@RequestMapping("/leave-policies")
public class LeavePolicyQueryController {

  private final LeavePolicyQueryService leavePolicyQueryService;

  @GetMapping
  public ApiResponse<List<LeavePolicy>> getPolicies(
      @CurrentUser AuthUser authUser
  ) {
    return ApiResponse.success(
        leavePolicyQueryService.getPolicies(authUser.companyId())
    );


  }
}