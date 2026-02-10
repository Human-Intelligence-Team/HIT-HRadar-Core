package org.hit.hradar.domain.company.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.company.query.dto.CompanyDetailResponse;
import org.hit.hradar.domain.company.query.dto.CompanyResponse;
import org.hit.hradar.domain.company.query.dto.CompanySearchRequest;
import org.hit.hradar.domain.company.query.service.CompanyQueryService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.hit.hradar.global.query.paging.PageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Company Query Controller
 * - 바인딩은 Condition DTO로 받고, 컨트롤러는 호출만 담당
 */
@Tag(name = "Company Query", description = "회사 정보 조회 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/company")
public class CompanyQueryController {

  private final CompanyQueryService companyQueryService;

  @Operation(summary = "내 회사 정보 조회", description = "현재 로그인한 사용자가 속한 회사의 상세 정보를 조회합니다.")
  @GetMapping("/my")
  public ResponseEntity<ApiResponse<CompanyDetailResponse>> getMyCompany(
      @CurrentUser AuthUser authUser) {
    Long companyId = authUser.companyId();
    CompanyDetailResponse response = companyQueryService.getMyCompany(companyId);
    return ResponseEntity.ok(ApiResponse.success(response));
  }

  @Operation(summary = "회사 목록 조회", description = "조건에 맞는 회사 목록을 조회합니다.")
  @GetMapping
  public ApiResponse<PageResponse<CompanyResponse>> getCompanies(
      @ModelAttribute CompanySearchRequest req) {
    return ApiResponse.success(companyQueryService.getCompanies(req));
  }

  @Operation(summary = "회사 상세 정보 조회", description = "회사 ID로 특정 회사의 상세 정보를 조회합니다.")
  @GetMapping("/{companyId}")
  public ResponseEntity<ApiResponse<CompanyDetailResponse>> getCompany(
      @PathVariable Long companyId,
      @CurrentUser AuthUser authUser) {
    CompanyDetailResponse response = companyQueryService.getCompany(companyId, authUser.companyId(), authUser.role());
    return ResponseEntity.ok(ApiResponse.success(response));
  }
}
