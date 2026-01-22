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

@RestController
@RequiredArgsConstructor
@RequestMapping("/company")
public class CompanyCommandController {

  private final CompanyCommandService companyCommandService;


  @PatchMapping("/{comId}")
  public ResponseEntity<UpdateCompanyResponse> update(
      @CurrentUser AuthUser authUser,                   // 호출자 식별
      @PathVariable Long comId,                          // 수정 대상 회사 PK
      @Valid @RequestBody UpdateCompanyRequest request   // 수정 요청 DTO
  ) {
    Long requesterAccId = authUser.userId(); // 권한 판단용
    Long requesterComId = authUser.companyId();  // 자기 회사인지 판단용(필요 시)

    UpdateCompanyResponse response =
        companyCommandService.updateCompany(comId, request, requesterAccId, requesterComId);

    return ResponseEntity.ok(response);
  }


  @DeleteMapping("/{comId}")
  public ResponseEntity<Void> delete(
      @CurrentUser AuthUser authUser, // 호출자 식별
      @PathVariable Long comId         // 삭제 대상 회사 PK
  ) {
    Long requesterAccId = authUser.userId(); // 권한 판단용

    companyCommandService.deleteCompany(comId, requesterAccId);

    return ResponseEntity.ok().build();
  }
}
