package org.hit.hradar.domain.company.command.application.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.company.command.application.dto.request.UpdateCompanyRequest;
import org.hit.hradar.domain.company.command.application.dto.response.UpdateCompanyResponse;
import org.hit.hradar.domain.company.command.application.service.CompanyCommandService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 회사 Command API 컨트롤러
 * - HTTP 바인딩 + 서비스 위임만 수행
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/company")
public class CompanyCommandController {

  private final CompanyCommandService companyCommandService;

  @PatchMapping("/{comId}")
  public ResponseEntity<UpdateCompanyResponse> update(
      @CurrentUser AuthUser authUser,                 // 인증 주체
      @PathVariable Long comId,                       // 수정 대상 회사 PK
      @Valid @RequestBody UpdateCompanyRequest request // 수정 요청 DTO
  ) {
    // 컨트롤러에서 accId/comId 분해하지 않고, authUser를 그대로 서비스에 위임
    UpdateCompanyResponse response = companyCommandService.updateCompany(comId, request, authUser);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{comId}")
  public ResponseEntity<Void> delete(
      @CurrentUser AuthUser authUser, // 인증 주체
      @PathVariable Long comId        // 삭제 대상 회사 PK
  ) {
    // 컨트롤러는 서비스 호출만
    companyCommandService.deleteCompany(comId, authUser);
    return ResponseEntity.ok().build();
  }
}
