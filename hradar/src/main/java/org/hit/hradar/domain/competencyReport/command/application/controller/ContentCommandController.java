package org.hit.hradar.domain.competencyReport.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.competencyReport.command.application.dto.request.ContentCreateRequest;
import org.hit.hradar.domain.competencyReport.command.application.dto.request.ContentUpdateRequest;
import org.hit.hradar.domain.competencyReport.command.application.dto.response.ContentUpdateResponse;
import org.hit.hradar.domain.competencyReport.command.application.service.ContentsCommandService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Competency Report Content Command", description = "학습 컨텐츠 관리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/learning-contents")
public class ContentCommandController {

  private final ContentsCommandService contentsCommandService;

  /**
   * 학습 컨텐츠 등록
   * 
   * @param request
   * @return
   */
  @Operation(summary = "학습 컨텐츠 등록", description = "새로운 학습 컨텐츠를 등록합니다.")
  @PostMapping
  public ResponseEntity<ApiResponse<Void>> createContent(
      @CurrentUser AuthUser authUser,
      @RequestBody @Valid ContentCreateRequest request) {

    Long comId = authUser.companyId();
    contentsCommandService.createContents(request, comId);
    return ResponseEntity.ok(ApiResponse.success(null));
  }

  /**
   * 학습 컨텐츠 수정
   * 
   * @param request
   * @return
   */
  @Operation(summary = "학습 컨텐츠 수정", description = "기존 학습 컨텐츠 정보를 수정합니다.")
  @PutMapping
  public ResponseEntity<ApiResponse<ContentUpdateResponse>> updateContent(
      @CurrentUser AuthUser authUser,
      @RequestBody @Valid ContentUpdateRequest request) {

    Long comId = authUser.companyId();
    ContentUpdateResponse response = contentsCommandService.updateContent(request, comId);
    return ResponseEntity.ok(ApiResponse.success(response));
  }

  /**
   * 학습 컨텐츠 삭제
   */
  @DeleteMapping("/{contentId}")
  public ResponseEntity<ApiResponse<Void>> deleteContent(
      @CurrentUser AuthUser authUser,
      @PathVariable("contentId") Long contentId
  ) {

    Long comId = authUser.companyId();
    contentsCommandService.deleteContent(contentId, comId);
    return ResponseEntity.ok(ApiResponse.success(null));
  }


}
