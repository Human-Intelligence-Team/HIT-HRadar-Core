package org.hit.hradar.domain.company.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.company.command.application.dto.request.UpdateCompanyRequest;
import org.hit.hradar.domain.company.command.application.dto.response.UpdateCompanyResponse;
import org.hit.hradar.domain.company.command.application.service.CompanyCommandService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 회사 Command API 컨트롤러
 */
@Tag(name = "Company Command", description = "회사 정보 관리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/company")
public class CompanyCommandController {

  private final CompanyCommandService companyCommandService;

  @Operation(summary = "회사 정보 수정", description = "회사의 정보를 수정합니다.")
  @PatchMapping("/{comId}")
  public ResponseEntity<ApiResponse<UpdateCompanyResponse>> update(
      @PathVariable Long comId,
      @RequestBody @Valid UpdateCompanyRequest request,
      @CurrentUser AuthUser authUser) {
    Long companyId = authUser.companyId();
    UpdateCompanyResponse response = companyCommandService.updateCompany(comId, companyId, request);
    return ResponseEntity.ok(ApiResponse.success(response));
  }

  @Operation(summary = "회사 삭제", description = "회사를 삭제합니다.")
  @DeleteMapping("/{comId}")
  public ResponseEntity<ApiResponse<Void>> delete(
      @PathVariable Long comId,
      @CurrentUser AuthUser authUser) {
    Long companyId = authUser.companyId();
    companyCommandService.deleteCompany(comId, companyId, authUser.role());
    return ResponseEntity.ok(ApiResponse.success(null));
  }
}
