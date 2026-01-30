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
    Long companyId = authUser.companyId();
    CompanyDetailResponse response = companyQueryService.getMyCompany(companyId);
    return ResponseEntity.ok(ApiResponse.success(response));
  }

  @GetMapping
  public ResponseEntity<ApiResponse<List<CompanyResponse>>> getCompanies(
      CompanySearchCondition condition
  ) {
    List<CompanyResponse> response = companyQueryService.getCompanies(condition);
    return ResponseEntity.ok(ApiResponse.success(response));
  }

  @GetMapping("/{companyId}")
  public ResponseEntity<ApiResponse<CompanyDetailResponse>> getCompany(
      @PathVariable Long companyId
  ) {
    CompanyDetailResponse response = companyQueryService.getCompany(companyId);
    return ResponseEntity.ok(ApiResponse.success(response));
  }
}
