package org.hit.hradar.domain.positions.query.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.positions.query.dto.PositionResponse;
import org.hit.hradar.domain.positions.query.service.PositionQueryService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/positions")
public class PositionQueryController {

  private final PositionQueryService positionQueryService;

  @GetMapping("/{positionId}")
  public ResponseEntity<ApiResponse<PositionResponse>> getPositionById(
      @PathVariable Long positionId,
      @CurrentUser AuthUser authUser
  ) {
    PositionResponse response = positionQueryService.getPositionById(positionId, authUser.companyId());
    return ResponseEntity.ok(ApiResponse.success(response));
  }

  @GetMapping
  public ResponseEntity<ApiResponse<List<PositionResponse>>> getAllPositionsByCompany(
      @CurrentUser AuthUser authUser
  ) {
    List<PositionResponse> responses = positionQueryService.getAllPositionsByCompany(authUser.companyId());
    return ResponseEntity.ok(ApiResponse.success(responses));
  }
}
