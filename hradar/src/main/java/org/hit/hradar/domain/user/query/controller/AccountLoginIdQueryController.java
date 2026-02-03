package org.hit.hradar.domain.user.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.user.query.dto.AccountLoginIdResponse;
import org.hit.hradar.domain.user.query.service.UserAccountQueryService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User Account Admin Query", description = "관리자용 사용자 계정 정보 조회 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/adminAccounts")
public class AccountLoginIdQueryController {

  private final UserAccountQueryService userAccountQueryService;

  @Operation(summary = "사용자 로그인 ID 조회", description = "특정 계정의 실제 로그인 ID를 조회합니다. (관리자 전용)")
  @GetMapping("/{accId}/login-id")
  public ResponseEntity<ApiResponse<AccountLoginIdResponse>> getLoginId(
      @PathVariable Long accId,
      @CurrentUser AuthUser authUser) {
    Long comId = authUser.companyId();
    String role = authUser.role();

    AccountLoginIdResponse res = userAccountQueryService.getLoginIdAsAdmin(comId, role, accId);
    return ResponseEntity.ok(ApiResponse.success(res));
  }
}
