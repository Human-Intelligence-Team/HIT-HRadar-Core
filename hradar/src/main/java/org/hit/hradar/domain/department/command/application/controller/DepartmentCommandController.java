package org.hit.hradar.domain.department.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid; // 유효성 검증을 위해 추가
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.department.command.application.dto.CreateDepartmentRequest;
import org.hit.hradar.domain.department.command.application.dto.UpdateDepartmentRequest;
import org.hit.hradar.domain.department.command.application.service.DepartmentCommandService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Department Command", description = "부서 생성, 수정, 삭제 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/departments")
public class DepartmentCommandController {

  // 부서 생성/수정/삭제 처리 서비스
  private final DepartmentCommandService departmentCommandService;

  /**
   * 부서 등록
   * 
   * @param request  부서 생성 요청 DTO
   * @param authUser 현재 로그인 사용자(회사 식별자 포함)
   * @return 생성된 deptId
   */
  @Operation(summary = "부서 등록", description = "새로운 부서를 등록합니다.")
  @PostMapping
  public ResponseEntity<ApiResponse<Long>> createDepartment(
      @RequestBody @Valid CreateDepartmentRequest request,
      @CurrentUser AuthUser authUser) {

    Long deptId = departmentCommandService.createDepartment(request, authUser.companyId());

    return ResponseEntity.ok(ApiResponse.success(deptId));
  }

  /**
   * 부서 수정
   * 
   * @param deptId   수정 대상 부서 ID
   * @param request  부서 수정 요청 DTO
   * @param authUser 현재 로그인 사용자(회사 식별자 포함)
   * @return 처리 결과(Void)
   */
  @Operation(summary = "부서 수정", description = "기존 부서 정보를 수정합니다.")
  @PatchMapping("/{deptId}")
  public ResponseEntity<ApiResponse<Void>> updateDepartment(
      @PathVariable Long deptId,
      @RequestBody @Valid UpdateDepartmentRequest request,
      @CurrentUser AuthUser authUser) {

    departmentCommandService.updateDepartment(deptId, authUser.companyId(), request);

    return ResponseEntity.ok(ApiResponse.success(null));
  }

  /**
   * 부서 삭제
   * 
   * @param deptId   삭제 대상 부서 ID
   * @param authUser 현재 로그인 사용자(회사 식별자 포함)
   * @return 처리 결과(Void)
   */
  @Operation(summary = "부서 삭제", description = "부서를 삭제합니다.")
  @DeleteMapping("/{deptId}")
  public ResponseEntity<ApiResponse<Void>> deleteDepartment(
      @PathVariable Long deptId,
      @CurrentUser AuthUser authUser) {

    departmentCommandService.deleteDepartment(deptId, authUser.companyId());

    return ResponseEntity.ok(ApiResponse.success(null));
  }
}
