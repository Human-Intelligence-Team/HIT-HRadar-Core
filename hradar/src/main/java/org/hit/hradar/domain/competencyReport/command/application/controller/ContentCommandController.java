package org.hit.hradar.domain.competencyReport.command.application.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.hit.hradar.domain.competencyReport.command.application.dto.request.ContentsRequest;
import org.hit.hradar.domain.competencyReport.command.application.service.ContentsCommandService;
import org.hit.hradar.global.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/learning-contents")
public class ContentCommandController {

  private final ContentsCommandService contentsCommandService;

  /**
   * 학습 컨텐츠 등록
   * @param request
   * @return
   */
  @PostMapping
  public ResponseEntity<ApiResponse<Void>> createContent(
      @RequestBody @Valid ContentsRequest request
  ) throws BadRequestException {

    contentsCommandService.createContents(request);
    return ResponseEntity.ok(ApiResponse.success(null));
  }
}
