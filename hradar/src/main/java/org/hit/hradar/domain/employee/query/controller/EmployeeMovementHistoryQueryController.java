package org.hit.hradar.domain.employee.query.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.employee.query.dto.EmployeeMovementHistoryResponse;
import org.hit.hradar.domain.employee.query.service.EmployeeMovementHistoryQueryService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeMovementHistoryQueryController {

  private final EmployeeMovementHistoryQueryService historyQueryService;

  @GetMapping("/{empId}/movement-histories")
  public ResponseEntity<ApiResponse<List<EmployeeMovementHistoryResponse>>> getMovementHistories(
      @CurrentUser AuthUser authUser,
      @PathVariable Long empId
  ) {
    List<EmployeeMovementHistoryResponse> list =
        historyQueryService.getHistory(authUser.companyId(), empId);

    return ResponseEntity.ok(ApiResponse.success(list));
  }
}
