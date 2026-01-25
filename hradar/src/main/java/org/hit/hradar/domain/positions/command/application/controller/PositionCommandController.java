package org.hit.hradar.domain.positions.command.application.controller;

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

@RestController
@RequiredArgsConstructor
@RequestMapping("/positions")
public class PositionCommandController {

  private final PositionCommandService positionCommandService;

  @PostMapping
  public ResponseEntity<ApiResponse<Long>> createPosition(
      @Valid @RequestBody CreatePositionRequest request,
      @CurrentUser AuthUser authUser
  ) {
    Long positionId = positionCommandService.createPosition(request, authUser.companyId());
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(ApiResponse.success(positionId));
  }

  @PatchMapping("/{positionId}")
  public ResponseEntity<ApiResponse<Void>> updatePosition(
      @PathVariable Long positionId,
      @Valid @RequestBody UpdatePositionRequest request,
      @CurrentUser AuthUser authUser
  ) {
    positionCommandService.updatePosition(positionId, authUser.companyId(), request);
    return ResponseEntity.ok(ApiResponse.success(null));
  }

  @DeleteMapping("/{positionId}")
  public ResponseEntity<ApiResponse<Void>> deletePosition(
      @PathVariable Long positionId,
      @CurrentUser AuthUser authUser
  ) {
    positionCommandService.deletePosition(positionId, authUser.companyId());
    return ResponseEntity.ok(ApiResponse.success(null));
  }
}
