package org.hit.hradar.domain.companyApplication.query.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.companyApplication.query.dto.ComAppDetailResponse;
import org.hit.hradar.domain.companyApplication.query.dto.ComAppListItemResponse;
import org.hit.hradar.domain.companyApplication.query.dto.ComAppSearchRequest;
import org.hit.hradar.domain.companyApplication.query.service.ComAppQueryService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/company-applications")
public class ComAppQueryController {

  private final ComAppQueryService comAppQueryService;

  /**
   * 회사 신청 목록 조회(플랫폼 관리자만)
   */
  @GetMapping
  public ResponseEntity<ApiResponse<List<ComAppListItemResponse>>> search(
      @CurrentUser AuthUser authUser, ComAppSearchRequest request

  ) {
    List<ComAppListItemResponse> response = comAppQueryService.search(authUser, request);
    return ResponseEntity.ok(ApiResponse.success(response));
  }

  /**
   * 회사 신청 상세 조회(플랫폼 관리자만)
   */
  @GetMapping("/{applicationId}")
  public ResponseEntity<ApiResponse<ComAppDetailResponse>> detail(
      @CurrentUser AuthUser authUser,
      @PathVariable Long applicationId
  ) {
    ComAppDetailResponse response = comAppQueryService.getDetail(authUser, applicationId);
    return ResponseEntity.ok(ApiResponse.success(response));
  }
}
