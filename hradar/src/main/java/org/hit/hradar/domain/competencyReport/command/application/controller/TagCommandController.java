package org.hit.hradar.domain.competencyReport.command.application.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.competencyReport.command.application.dto.request.TagCreateRequest;
import org.hit.hradar.domain.competencyReport.command.application.dto.request.TagDeleteRequest;
import org.hit.hradar.domain.competencyReport.command.application.service.TagCommandService;
import org.hit.hradar.global.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tags")
public class TagCommandController {

  private final TagCommandService tagCommandService;

  /**
   * 태그 등록
   * @param request
   * @return
   */
  @PostMapping
  public ResponseEntity<ApiResponse<Void>> createTag(
      @RequestBody  @Valid TagCreateRequest request
  )  {

    tagCommandService.createTag(request);
    return ResponseEntity.ok(ApiResponse.success(null));
  }

  /**
   * 태그 삭제 (단건/다건)
   * @param tagDeleteRequest
   * @return
   */
  @PostMapping("/delete")
  public ResponseEntity<ApiResponse<Void>> deleteTag(
      @RequestBody TagDeleteRequest tagDeleteRequest
  ) {

    tagCommandService.deleteTag(tagDeleteRequest);
    return ResponseEntity.ok(ApiResponse.success(null));
  }

}
