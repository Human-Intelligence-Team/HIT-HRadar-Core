package org.hit.hradar.domain.user.query.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.user.query.dto.*;
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
  public ResponseEntity<ApiResponse<List<UserAccountListItemResponse>>> list(
      @CurrentUser AuthUser authUser,
      @ModelAttribute UserAccountSearchCondition condition
  ) {
    List<UserAccountListItemResponse> result = userAccountQueryService.getList(authUser, condition);
    return ResponseEntity.ok(ApiResponse.success(result));
  }

  @GetMapping("/{accId}")
  public ResponseEntity<ApiResponse<UserAccountDetailResponse>> detail(
      @CurrentUser AuthUser authUser,
      @PathVariable Long accId
  ) {
    UserAccountDetailResponse result = userAccountQueryService.getDetail(authUser, accId);
    return ResponseEntity.ok(ApiResponse.success(result));
  }
}
