package org.hit.hradar.domain.competencyReport.query.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.competencyReport.query.dto.request.ContentSearchRequest;
import org.hit.hradar.domain.competencyReport.query.dto.response.ContentSearchResponse;
import org.hit.hradar.domain.competencyReport.query.dto.response.TagSearchResponse;
import org.hit.hradar.domain.competencyReport.query.service.ContentQueryService;
import org.hit.hradar.global.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/learning-contents")
public class ContentQueryController {

  private final ContentQueryService contentQueryService;

  /**
   * 학습 콘텐츠 목록
   * @param request
   * @return
   */
  @GetMapping
  public ResponseEntity<ApiResponse<ContentSearchResponse>> contents(
      @ModelAttribute ContentSearchRequest request
  )  {

    ContentSearchResponse response = contentQueryService.contents(request);
    return ResponseEntity.ok(ApiResponse.success(response));
  }

}
