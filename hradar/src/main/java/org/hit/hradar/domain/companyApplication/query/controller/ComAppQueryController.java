package org.hit.hradar.domain.companyApplication.query.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.companyApplication.query.service.ComAppQueryService;
import org.hit.hradar.domain.companyApplication.query.dto.ComAppListItemResponse;
import org.hit.hradar.domain.companyApplication.query.dto.ComAppSearchRequest;
import org.hit.hradar.global.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/company-applications")
public class ComAppQueryController {

  private final ComAppQueryService comAppQueryService;

  @GetMapping
  public ResponseEntity<ApiResponse<List<ComAppListItemResponse>>> list(
      @ModelAttribute ComAppSearchRequest request
  ) {
    List<ComAppListItemResponse> result = comAppQueryService.search(request);
    return ResponseEntity.ok(ApiResponse.success(result));
  }
}
