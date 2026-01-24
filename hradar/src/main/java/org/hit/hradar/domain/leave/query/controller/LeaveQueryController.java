package org.hit.hradar.domain.leave.query.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.eclipse.angus.mail.iap.Response;
import org.hit.hradar.domain.leave.query.dto.response.LeaveDetailDto;
import org.hit.hradar.domain.leave.query.dto.response.LeaveGrantDto;
import org.hit.hradar.domain.leave.query.dto.response.LeaveListDto;
import org.hit.hradar.domain.leave.query.service.LeaveQueryService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/leave")
@RequiredArgsConstructor
public class LeaveQueryController {

  private final LeaveQueryService leaveQueryService;

  //내 휴가 목록 조회
  @GetMapping
  public ResponseEntity<ApiResponse<List<LeaveListDto>>> getMyLeaves(
      @CurrentUser AuthUser authUser
  ) {
    return ResponseEntity.ok(
        ApiResponse.success(
            leaveQueryService.getMyLeaveList(authUser.employeeId())
        )
    );
  }

  //휴가 상세 조회
  @GetMapping("/{leaveId}")
  public ResponseEntity<ApiResponse<LeaveDetailDto>> getLeaveDetail (
      @PathVariable Long leaveId
  ) {
    return ResponseEntity.ok(
        ApiResponse.success(
            leaveQueryService.getLeaveDetail(leaveId)
        )
    );
  }

  //연차 지급/잔여 조회
  @GetMapping("/grant/{grantId}")
  public ResponseEntity<ApiResponse<LeaveGrantDto>> getLeaveGrant(
      @PathVariable Long grantId
  ) {
    return ResponseEntity.ok(
        ApiResponse.success(
            leaveQueryService.getLeaveGrant(grantId)
        )
    );
  }



}
