package org.hit.hradar.domain.leave.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
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

@Tag(name = "Leave Query", description = "휴가 신청 내역 및 연차 잔여 일수 조회 API")
@RestController
@RequestMapping("/leave")
@RequiredArgsConstructor
public class LeaveQueryController {

    private final LeaveQueryService leaveQueryService;

    // 내 휴가 목록 조회
    @Operation(summary = "내 휴가 신청 목록 조회", description = "현재 로그인한 사원의 전체 휴가 신청 내역(승인/반려/대기)을 조회합니다.")
    @GetMapping
    public ResponseEntity<ApiResponse<List<LeaveListDto>>> getMyLeaves(
            @CurrentUser AuthUser authUser) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        leaveQueryService.getMyLeaveList(authUser.employeeId())));
    }

    // 부서 휴가 목록 조회
    @Operation(summary = "부서 휴가 목록 조회", description = "로그인한 사용자의 부서 휴가 목록을 조회합니다.")
    @GetMapping("/department")
    public ResponseEntity<ApiResponse<List<LeaveListDto>>> getDepartmentLeaves(
            @CurrentUser AuthUser authUser) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        leaveQueryService.getDepartmentLeaveList(authUser.employeeId()))); 
    }

    // 휴가 상세 조회
    @Operation(summary = "휴가 상세 정보 조회", description = "특정 휴가 신청 건의 상세 정보(사유, 일시, 상태 등)를 조회합니다.")
    @GetMapping("/{leaveId}")
    public ResponseEntity<ApiResponse<LeaveDetailDto>> getLeaveDetail(
            @PathVariable Long leaveId) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        leaveQueryService.getLeaveDetail(leaveId)));
    }

    // 연차 지급/잔여 조회
    @Operation(summary = "특정 연차 지급 정보 조회", description = "특정 연차 발생/지정 건에 대한 세부 일수 및 잔여 기간을 조회합니다.")
    @GetMapping("/grant/{grantId}")
    public ResponseEntity<ApiResponse<LeaveGrantDto>> getLeaveGrant(
            @PathVariable Long grantId) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        leaveQueryService.getLeaveGrant(grantId)));
    }

    // 내 연차 지급 목록 조회
    @Operation(summary = "내 연차 실적 정보 조회", description = "사원 본인이 현재 보유한 전체 연차 발생 건들의 목록과 각각의 잔여 일수를 조회합니다.")
    @GetMapping("/grants")
    public ResponseEntity<ApiResponse<List<LeaveGrantDto>>> getMyLeaveGrants(
            @CurrentUser AuthUser authUser) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        leaveQueryService.getMyLeaveGrants(authUser.employeeId())));
    }

}
