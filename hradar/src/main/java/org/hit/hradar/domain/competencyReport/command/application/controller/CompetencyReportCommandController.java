package org.hit.hradar.domain.competencyReport.command.application.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.competencyReport.command.application.dto.request.CompetencyReportCreateRequest;
import org.hit.hradar.domain.competencyReport.command.application.service.CompetencyReportCommandService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/competency-report")
public class CompetencyReportCommandController {

  private final CompetencyReportCommandService competencyReportCommandService;

  /**
   * 학습 컨텐츠 등록
   * @return
   */
  @PostMapping()
  public ResponseEntity<ApiResponse<Void>> createReport(
      @CurrentUser AuthUser authUser,
      @RequestBody CompetencyReportCreateRequest request
  )  {

    Long comId = authUser.companyId();
    competencyReportCommandService.createReport(comId, request);
    return ResponseEntity.ok(ApiResponse.success(null));
  }


}
