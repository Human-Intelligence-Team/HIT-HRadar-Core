package org.hit.hradar.domain.department.command.application.controller;

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

/**
 * 부서 Command API 컨트롤러
 * - ContentCommandController 스타일(메서드 주석, ResponseEntity.ok, @Valid 사용)에 맞춘 형태
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/departments")
public class DepartmentCommandController {

  // 부서 생성/수정/삭제 처리 서비스
  private final DepartmentCommandService departmentCommandService;

  /**
   * 부서 등록
   * @param request 부서 생성 요청 DTO
   * @param authUser 현재 로그인 사용자(회사 식별자 포함)
   * @return 생성된 deptId
   */
  @PostMapping
  public ResponseEntity<ApiResponse<Long>> createDepartment(
      @RequestBody @Valid CreateDepartmentRequest request, // 요청값 유효성 검증
      @CurrentUser AuthUser authUser // AOP로 주입되는 인증 사용자
  ) {
    // 서비스에 생성 요청 위임 (회사 단위 멀티테넌시 처리)
    Long deptId = departmentCommandService.createDepartment(request, authUser.companyId());

    // ContentController 스타일처럼 ok()로 반환 (원하면 created로 다시 바꿔도 됨)
    return ResponseEntity.ok(ApiResponse.success(deptId));
  }

  /**
   * 부서 수정
   * @param deptId 수정 대상 부서 ID
   * @param request 부서 수정 요청 DTO
   * @param authUser 현재 로그인 사용자(회사 식별자 포함)
   * @return 처리 결과(Void)
   */
  @PutMapping("/{deptId}") // ContentController가 PUT을 쓰는 스타일에 맞춤
  public ResponseEntity<ApiResponse<Void>> updateDepartment(
      @PathVariable Long deptId, // URL 경로에서 부서 ID 수신
      @RequestBody @Valid UpdateDepartmentRequest request, // 요청값 유효성 검증
      @CurrentUser AuthUser authUser // 회사 스코프 검증에 사용
  ) {
    // 서비스에 수정 요청 위임
    departmentCommandService.updateDepartment(deptId, authUser.companyId(), request);

    // 성공 응답 (payload 없음)
    return ResponseEntity.ok(ApiResponse.success(null));
  }

  /**
   * 부서 삭제
   * @param deptId 삭제 대상 부서 ID
   * @param authUser 현재 로그인 사용자(회사 식별자 포함)
   * @return 처리 결과(Void)
   */
  @DeleteMapping("/{deptId}")
  public ResponseEntity<ApiResponse<Void>> deleteDepartment(
      @PathVariable Long deptId, // URL 경로에서 부서 ID 수신
      @CurrentUser AuthUser authUser // 회사 스코프 검증에 사용
  ) {
    // 서비스에 삭제 요청 위임
    departmentCommandService.deleteDepartment(deptId, authUser.companyId());

    // 성공 응답 (payload 없음)
    return ResponseEntity.ok(ApiResponse.success(null));
  }
}
