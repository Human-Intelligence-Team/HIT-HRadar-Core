package org.hit.hradar.domain.employee.command.application.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.employee.command.application.dto.CsvImportEmployeesResponse;
import org.hit.hradar.domain.employee.command.application.service.EmployeeOnboardingCommandService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/employees")
public class EmployeeOnboardingCommandController {

  private final EmployeeOnboardingCommandService onboardingCommandService;

  /**
   * CSV 업로드로 사원 + 계정 생성
   * CSV 예시 헤더(권장):
   * name,employeeNo,email,deptId,positionId,gender,birth,hireDate,phoneNo,loginId,password
   */
  @PostMapping("/import-csv")
  public ResponseEntity<ApiResponse<CsvImportEmployeesResponse>> importEmployeesCsv(
      @RequestPart("file") MultipartFile file,
      @CurrentUser AuthUser authUser
  ) {
    CsvImportEmployeesResponse result =
        onboardingCommandService.importEmployeesAndAccountsFromCsv(file, authUser.companyId());

    return ResponseEntity.ok(ApiResponse.success(result));
  }
}
