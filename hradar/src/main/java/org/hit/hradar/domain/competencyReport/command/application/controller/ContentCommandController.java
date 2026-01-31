package org.hit.hradar.domain.competencyReport.command.application.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.competencyReport.command.application.dto.request.ContentCreateRequest;
import org.hit.hradar.domain.competencyReport.command.application.dto.request.ContentUpdateRequest;
import org.hit.hradar.domain.competencyReport.command.application.dto.response.ContentUpdateResponse;
import org.hit.hradar.domain.competencyReport.command.application.service.ContentsCommandService;
import org.hit.hradar.global.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/learning-contents")
public class ContentCommandController {

  private final ContentsCommandService contentsCommandService;

  /**
   * 학습 컨텐츠 등록
   * @param request
   * @return
   */
  @PostMapping
  public ResponseEntity<ApiResponse<Void>> createContent(
      @RequestBody @Valid ContentCreateRequest request
  )  {

    contentsCommandService.createContents(request);
    return ResponseEntity.ok(ApiResponse.success(null));
  }

  /**
   * 학습 컨텐츠 수정
   * @param request
   * @return
   */
  @PutMapping
  public ResponseEntity<ApiResponse<ContentUpdateResponse>> updateContent(
      @RequestBody @Valid ContentUpdateRequest request
  )  {

    ContentUpdateResponse response = contentsCommandService.updateContent(request);
    return ResponseEntity.ok(ApiResponse.success(response));
  }

  /**
   * 학습 컨텐츠 삭제
   */
  @DeleteMapping("/{contentId}")
  public ResponseEntity<ApiResponse<Void>> deleteContent(
      @PathVariable Long contentId
  ) {

    contentsCommandService.deleteContent(contentId);
    return ResponseEntity.ok(ApiResponse.success(null));
  }


}
