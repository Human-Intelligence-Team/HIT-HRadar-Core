package org.hit.hradar.domain.company.query.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.company.query.dto.CompanyDetailResponse;
import org.hit.hradar.domain.company.query.dto.CompanyResponse;
import org.hit.hradar.domain.company.query.dto.CompanySearchCondition;
import org.hit.hradar.domain.company.query.service.CompanyQueryService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Company Query Controller
 * - 바인딩은 Condition DTO로 받고, 컨트롤러는 호출만 담당
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/company")
public class CompanyQueryController {

  private final CompanyQueryService companyQueryService;

  @GetMapping("/my")
  public ResponseEntity<ApiResponse<CompanyDetailResponse>> getMyCompany(
      @CurrentUser AuthUser authUser
  ) {
    return ResponseEntity.ok(ApiResponse.success(companyQueryService.getMyCompany(authUser)));
  }

  @GetMapping
  public ResponseEntity<ApiResponse<List<CompanyResponse>>> getCompanies(
      CompanySearchCondition condition
  ) {
    return ResponseEntity.ok(ApiResponse.success(companyQueryService.getCompanies(condition)));
  }

  @GetMapping("/{companyId}")
  public ResponseEntity<ApiResponse<CompanyDetailResponse>> getCompany(
      @PathVariable Long companyId
  ) {
    return ResponseEntity.ok(ApiResponse.success(companyQueryService.getCompany(companyId)));
  }
}
