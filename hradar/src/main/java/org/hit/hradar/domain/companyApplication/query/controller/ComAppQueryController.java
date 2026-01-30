package org.hit.hradar.domain.companyApplication.query.controller;


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

@RestController
@RequiredArgsConstructor
@RequestMapping("/company-applications")

public class ComAppQueryController {

  private final ComAppQueryService comAppQueryService;

  @GetMapping
  public ResponseEntity<ApiResponse<ComAppSearchResponse>> search(
      ComAppSearchRequest request,
      @CurrentUser AuthUser authUser
  ) {
    Long userId = authUser.userId();
    String role = authUser.role();

    ComAppSearchResponse response =
        comAppQueryService.search(userId, role, request);

    return ResponseEntity.ok(ApiResponse.success(response));
  }

  @GetMapping("/{applicationId}")
  public ResponseEntity<ApiResponse<ComAppDetailResponse>> detail(
      @PathVariable Long applicationId,
      @CurrentUser AuthUser authUser
  ) {
    Long userId = authUser.userId();
    String role = authUser.role();

    ComAppDetailResponse response =
        comAppQueryService.getDetail(userId, role, applicationId);

    return ResponseEntity.ok(ApiResponse.success(response));
  }
}
