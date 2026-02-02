package org.hit.hradar.domain.salary.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.salary.command.application.dto.request.CommonApprovalRequest;
//import org.hit.hradar.domain.salary.command.application.dto.request.SalaryApprovalRequest;
//import org.hit.hradar.domain.salary.command.application.dto.request.SalaryCreateRequest;
import org.hit.hradar.domain.salary.command.application.service.SalaryCommandService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Salary Command", description = "급여 관련 커맨드 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/salary")
public class SalaryCommandController {

  private final SalaryCommandService salaryCommandService;

  /**
   * 연봉 결재 등록
   * 
   * @param commonApprovalRequest
   * @param authUser
   * @return
   */
  @Operation(summary = "연봉 결재 등록", description = "새로운 연봉 계약에 대한 결재 요청을 등록합니다.")
  @PostMapping
  public ResponseEntity<ApiResponse<Void>> create(
      @RequestBody CommonApprovalRequest commonApprovalRequest,
      @CurrentUser AuthUser authUser) {

    Long empId = authUser.employeeId();
    salaryCommandService.createSalaryApproval(commonApprovalRequest, empId);
    return ResponseEntity.ok(ApiResponse.success(null));
  }

}
