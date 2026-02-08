package org.hit.hradar.domain.competencyReport.query.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.competencyReport.command.application.dto.request.CustomCodeSearchRequest;
import org.hit.hradar.domain.competencyReport.query.dto.response.CustomCodeSearchResponse;
import org.hit.hradar.domain.competencyReport.query.service.CustomCodeQueryService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contents/custom-code")
public class CustomCodeQueryController {

  private final CustomCodeQueryService customCodeQueryService;

  /**
   * 커스텀 코드 group code 조회
   * 
   * @return
   */
  @GetMapping("/groups")
  public ResponseEntity<ApiResponse<CustomCodeSearchResponse>> customCodeByGroups(
      @CurrentUser AuthUser authUser) {

    Long comId = authUser.companyId();
    CustomCodeSearchResponse response = customCodeQueryService.customCodeByGroups(comId);
    return ResponseEntity.ok(ApiResponse.success(response));
  }

  /**
   * 커스텀 코드 group code 조회
   * 
   * @return
   */
  @GetMapping("/customCodes")
  public ResponseEntity<ApiResponse<CustomCodeSearchResponse>> customCodesByCustomCode(
      @CurrentUser AuthUser authUser,
      CustomCodeSearchRequest request

  ) {
    Long comId = authUser.companyId();
    CustomCodeSearchResponse response = customCodeQueryService.customCodesByCustomCode(comId, request);
    return ResponseEntity.ok(ApiResponse.success(response));
  }

  /**
   * 커스텀 코드들 조회
   * 
   * @return
   */
  @GetMapping()
  public ResponseEntity<ApiResponse<CustomCodeSearchResponse>> customCodes(
      @CurrentUser AuthUser authUser) {

    Long comId = authUser.companyId();

    CustomCodeSearchResponse response = customCodeQueryService.customCodes(comId);
    return ResponseEntity.ok(ApiResponse.success(response));
  }

}
