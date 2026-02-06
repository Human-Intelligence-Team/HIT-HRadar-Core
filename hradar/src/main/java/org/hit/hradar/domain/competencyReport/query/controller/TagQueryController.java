package org.hit.hradar.domain.competencyReport.query.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.competencyReport.query.dto.response.TagSearchResponse;
import org.hit.hradar.domain.competencyReport.query.dto.request.TagSearchRequest;
import org.hit.hradar.domain.competencyReport.query.service.TagQueryService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tags")
public class TagQueryController {

  private final TagQueryService tagQueryService;

  /**
   * 태그 목록
   * 
   * @param request
   * @return
   */
  @GetMapping
  public ResponseEntity<ApiResponse<TagSearchResponse>> tags(
      @CurrentUser AuthUser authUser,
      TagSearchRequest request) {

    Long comId =  authUser.companyId();
    TagSearchResponse response = tagQueryService.tags(request, comId);
    return ResponseEntity.ok(ApiResponse.success(response));
  }

  /**
   * 태그 목록
   * 
   * @return
   */
  @GetMapping("/{tagName}")
  public ResponseEntity<ApiResponse<TagSearchResponse>> tagsByTagName(
      @CurrentUser AuthUser authUser,
      @PathVariable("tagName") String tagName
  ) {
    Long comId =  authUser.companyId();
    TagSearchResponse response = tagQueryService.tagsByTagName(tagName, comId);
    return ResponseEntity.ok(ApiResponse.success(response));
  }

}
