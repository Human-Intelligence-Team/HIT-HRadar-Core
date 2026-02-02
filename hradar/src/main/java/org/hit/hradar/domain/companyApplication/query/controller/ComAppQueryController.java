package org.hit.hradar.domain.companyApplication.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.companyApplication.query.dto.ComAppDetailResponse;
import org.hit.hradar.domain.companyApplication.query.dto.ComAppSearchRequest;
import org.hit.hradar.domain.companyApplication.query.dto.ComAppSearchResponse;
import org.hit.hradar.domain.companyApplication.query.service.ComAppQueryService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Company Application Query", description = "회사 신청 조회 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/company-applications")

public class ComAppQueryController {

  private final ComAppQueryService comAppQueryService;

  @Operation(summary = "회사 신청 목록 조회", description = "가입 신청된 회사 목록을 조회합니다.")
  @GetMapping
  public ResponseEntity<ApiResponse<ComAppSearchResponse>> search(
      ComAppSearchRequest request,
      @CurrentUser AuthUser authUser) {
    Long userId = authUser.userId();
    String role = authUser.role();

    ComAppSearchResponse response = comAppQueryService.search(userId, role, request);

    return ResponseEntity.ok(ApiResponse.success(response));
  }

  @Operation(summary = "회사 신청 상세 조회", description = "신청 ID로 특정 회사의 가입 신청 상세 정보를 조회합니다.")
  @GetMapping("/{applicationId}")
  public ResponseEntity<ApiResponse<ComAppDetailResponse>> detail(
      @PathVariable Long applicationId,
      @CurrentUser AuthUser authUser) {
    Long userId = authUser.userId();
    String role = authUser.role();

    ComAppDetailResponse response = comAppQueryService.getDetail(userId, role, applicationId);

    return ResponseEntity.ok(ApiResponse.success(response));
  }
}
