package org.hit.hradar.domain.user.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "User Account Query", description = "사용자 계정 정보 조회 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/user-accounts")
public class UserAccountQueryController {

  private final UserAccountQueryService userAccountQueryService;

  @Operation(summary = "계정 목록 조회", description = "회사 내 모든 사용자 계정 목록을 검색 조건(상태, 역할 등)에 따라 조회합니다.")
  @GetMapping
  public ResponseEntity<ApiResponse<UserAccountListResponse>> list(
      @ModelAttribute UserAccountSearchCondition condition,
      @CurrentUser AuthUser authUser) {
    Long comId = authUser.companyId();

    UserAccountListResponse result = userAccountQueryService.getList(comId, condition);
    return ResponseEntity.ok(ApiResponse.success(result));
  }

  @Operation(summary = "계정 상세 조회", description = "특정 사용자 계정의 상세 정보(프로필, 역할, 상태 등)를 조회합니다.")
  @GetMapping("/{accId}")
  public ResponseEntity<ApiResponse<UserAccountDetailResponse>> detail(
      @PathVariable Long accId,
      @CurrentUser AuthUser authUser) {
    Long comId = authUser.companyId();

    UserAccountDetailResponse result = userAccountQueryService.getDetail(comId, accId);
    return ResponseEntity.ok(ApiResponse.success(result));
  }
}
