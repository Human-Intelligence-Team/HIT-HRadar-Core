package org.hit.hradar.domain.positions.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.positions.command.application.dto.CreatePositionRequest;
import org.hit.hradar.domain.positions.command.application.dto.UpdatePositionRequest;
import org.hit.hradar.domain.positions.command.application.service.PositionCommandService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Position Command", description = "직급 생성, 수정, 삭제 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/positions")
public class PositionCommandController {

  private final PositionCommandService positionCommandService;

  @Operation(summary = "직급 등록", description = "새로운 직급을 등록합니다.")
  @PostMapping
  public ResponseEntity<ApiResponse<Long>> createPosition(
      @Valid @RequestBody CreatePositionRequest request,
      @CurrentUser AuthUser authUser) {
    Long positionId = positionCommandService.createPosition(request, authUser.companyId());
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(ApiResponse.success(positionId));
  }

  @Operation(summary = "직급 수정", description = "기존 직급 정보를 수정합니다.")
  @PatchMapping("/{positionId}")
  public ResponseEntity<ApiResponse<Void>> updatePosition(
      @PathVariable Long positionId,
      @Valid @RequestBody UpdatePositionRequest request,
      @CurrentUser AuthUser authUser) {
    positionCommandService.updatePosition(positionId, authUser.companyId(), request);
    return ResponseEntity.ok(ApiResponse.success(null));
  }

  @Operation(summary = "직급 삭제", description = "직급을 삭제합니다.")
  @DeleteMapping("/{positionId}")
  public ResponseEntity<ApiResponse<Void>> deletePosition(
      @PathVariable Long positionId,
      @CurrentUser AuthUser authUser) {
    positionCommandService.deletePosition(positionId, authUser.companyId());
    return ResponseEntity.ok(ApiResponse.success(null));
  }
}
