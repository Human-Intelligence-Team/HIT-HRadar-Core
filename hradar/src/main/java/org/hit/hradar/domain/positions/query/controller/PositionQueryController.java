package org.hit.hradar.domain.positions.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.positions.query.dto.PositionListResponse;
import org.hit.hradar.domain.positions.query.dto.PositionResponse;
import org.hit.hradar.domain.positions.query.service.PositionQueryService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Position Query", description = "직급 정보 조회 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/positions")
public class PositionQueryController {

  private final PositionQueryService positionQueryService;

  @Operation(summary = "직급 상세 조회", description = "특정 직급의 세부 정보를 조회합니다.")
  @GetMapping("/{positionId}")
  public ResponseEntity<ApiResponse<PositionResponse>> getPositionById(
      @PathVariable Long positionId,
      @CurrentUser AuthUser authUser) {
    PositionResponse response = positionQueryService.getPositionById(positionId, authUser.companyId());

    return ResponseEntity.ok(ApiResponse.success(response));
  }

  @Operation(summary = "회사 전체 직급 목록 조회", description = "해당 회사에 등록된 모든 직급 목록을 조회합니다.")
  @GetMapping
  public ResponseEntity<ApiResponse<PositionListResponse>> getAllPositionsByCompany(
      @CurrentUser AuthUser authUser) {
    PositionListResponse response = positionQueryService.getAllPositionsByCompany(authUser.companyId());

    return ResponseEntity.ok(ApiResponse.success(response));
  }
}
