package org.hit.hradar.domain.competencyReport.command.application.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.competencyReport.command.application.dto.request.CustomCodeCreateRequest;
import org.hit.hradar.domain.competencyReport.command.application.dto.request.CustomCodeDeleteRequest;
import org.hit.hradar.domain.competencyReport.command.application.dto.request.TagDeleteRequest;
import org.hit.hradar.domain.competencyReport.command.application.dto.response.CustomCodeExistResponse;
import org.hit.hradar.domain.competencyReport.command.application.service.CustomCodeCommandService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contents/custom-code")
public class CustomCodeCommandController {

  private final CustomCodeCommandService customCodeCommandService;

  /**
   * 커스텀 코드 등록
   * @param request
   * @return
   */
  @PostMapping("")
  public ResponseEntity<ApiResponse<Void>> createContent(
      @CurrentUser AuthUser authUser,
      @RequestBody @Valid CustomCodeCreateRequest request
  )  {

    Long comId = authUser.companyId();
    customCodeCommandService.createCustomCode(request, comId);
    return ResponseEntity.ok(ApiResponse.success(null));
  }


  /**
   * 커스텀 코드 있는지 확인
   * @param authUser
   * @param customCode
   * @return
   */
  @GetMapping("existCustomCode")
  public ResponseEntity<ApiResponse<CustomCodeExistResponse>> existCustomCode(
      @CurrentUser AuthUser authUser,
      @RequestParam String customCode
  )  {

    Long comId = authUser.companyId();
    CustomCodeExistResponse response = customCodeCommandService.existCustomCode(customCode, comId);
    return ResponseEntity.ok(ApiResponse.success(response));
  }

  /**
   * 태그 삭제 (단건/다건)
   * @param request
   * @return
   */
  @PostMapping("/delete")
  public ResponseEntity<ApiResponse<Void>> deleteCustomCode(
      @CurrentUser AuthUser authUser,
      @RequestBody CustomCodeDeleteRequest request
  ) {

    Long comId = authUser.companyId();
    customCodeCommandService.deleteCustomCode(request, comId);
    return ResponseEntity.ok(ApiResponse.success(null));
  }

}
