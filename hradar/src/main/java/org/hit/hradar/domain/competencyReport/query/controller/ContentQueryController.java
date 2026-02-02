package org.hit.hradar.domain.competencyReport.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.competencyReport.query.dto.request.ContentSearchRequest;
import org.hit.hradar.domain.competencyReport.query.dto.response.ContentDetailResponse;
import org.hit.hradar.domain.competencyReport.query.dto.response.ContentSearchResponse;
import org.hit.hradar.domain.competencyReport.query.dto.response.TagSearchResponse;
import org.hit.hradar.domain.competencyReport.query.service.ContentQueryService;
import org.hit.hradar.global.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Competency Report Content Query", description = "학습 컨텐츠 조회 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/learning-contents")
public class ContentQueryController {

  private final ContentQueryService contentQueryService;

  /**
   * 학습 콘텐츠 목록
   * 
   * @param request
   * @return
   */
  @Operation(summary = "학습 컨텐츠 목록 조회", description = "등록된 모든 학습 컨텐츠 목록을 조회합니다.")
  @GetMapping
  public ResponseEntity<ApiResponse<ContentSearchResponse>> contents(
      ContentSearchRequest request) {

    ContentSearchResponse response = contentQueryService.contents(request);
    return ResponseEntity.ok(ApiResponse.success(response));
  }

  /**
   * 학습 콘텐츠 상세
   * 
   * @param id
   * @return
   */
  @Operation(summary = "학습 컨텐츠 상세 조회", description = "컨텐츠 ID로 학습 컨텐츠의 상세 정보를 조회합니다.")
  @GetMapping("{id}")
  public ResponseEntity<ApiResponse<ContentDetailResponse>> contentDetail(
      @PathVariable Long id) {

    ContentDetailResponse response = contentQueryService.contentDetail(id);
    return ResponseEntity.ok(ApiResponse.success(response));
  }
}
