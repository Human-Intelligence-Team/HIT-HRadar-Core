package org.hit.hradar.domain.user.query.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.user.query.dto.AccountLoginIdResponse;
import org.hit.hradar.domain.user.query.service.UserAccountQueryService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/adminAccounts")
public class AccountLoginIdQueryController {

  private final UserAccountQueryService userAccountQueryService;

  @GetMapping("/{accId}/login-id")
  public ResponseEntity<ApiResponse<AccountLoginIdResponse>> getLoginId(
      @PathVariable Long accId,
      @CurrentUser AuthUser authUser
  ) {
    Long comId = authUser.companyId();
    String role = authUser.role();

    AccountLoginIdResponse res = userAccountQueryService.getLoginIdAsAdmin(comId, role, accId);
    return ResponseEntity.ok(ApiResponse.success(res));
  }
}
