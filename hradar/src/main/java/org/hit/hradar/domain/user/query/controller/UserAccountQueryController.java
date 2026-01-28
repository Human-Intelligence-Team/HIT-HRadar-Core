package org.hit.hradar.domain.user.query.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.user.query.dto.UserAccountDetailResponse;
import org.hit.hradar.domain.user.query.dto.UserAccountListResponse;
import org.hit.hradar.domain.user.query.dto.UserAccountSearchCondition;
import org.hit.hradar.domain.user.query.service.UserAccountQueryService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user-accounts")
public class UserAccountQueryController {

  private final UserAccountQueryService userAccountQueryService;

  @GetMapping
  public ResponseEntity<ApiResponse<UserAccountListResponse>> list(
      @ModelAttribute UserAccountSearchCondition condition,
      @CurrentUser AuthUser authUser
  ) {
    Long comId = authUser.companyId();

    UserAccountListResponse result = userAccountQueryService.getList(comId, condition);
    return ResponseEntity.ok(ApiResponse.success(result));
  }

  @GetMapping("/{accId}")
  public ResponseEntity<ApiResponse<UserAccountDetailResponse>> detail(
      @PathVariable Long accId,
      @CurrentUser AuthUser authUser
  ) {
    Long comId = authUser.companyId();

    UserAccountDetailResponse result = userAccountQueryService.getDetail(comId, accId);
    return ResponseEntity.ok(ApiResponse.success(result));
  }
}
