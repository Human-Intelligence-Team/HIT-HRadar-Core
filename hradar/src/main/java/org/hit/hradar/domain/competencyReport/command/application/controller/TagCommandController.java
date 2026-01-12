package org.hit.hradar.domain.competencyReport.command.application.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.competencyReport.command.application.service.TagCommandService;
import org.hit.hradar.global.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tag")
public class TagCommandController {

  private final TagCommandService tagCommandService;

  /**
   * 태그 등록
   * @param tagName
   * @return
   */
  @PostMapping
  public ResponseEntity<ApiResponse<Void>> createTag(
     @RequestParam String tagName
  )  {

    tagCommandService.createTag(tagName);
    return ResponseEntity.ok(ApiResponse.success(null));
  }
}
