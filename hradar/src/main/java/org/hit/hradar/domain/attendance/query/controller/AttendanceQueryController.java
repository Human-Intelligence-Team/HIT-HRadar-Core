package org.hit.hradar.domain.attendance.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.attendance.query.dto.request.AttendanceDetailQueryRequest;
import org.hit.hradar.domain.attendance.query.dto.request.AttendanceListQueryRequest;
import org.hit.hradar.domain.attendance.query.dto.response.AttendanceCalendarItemDto;
import org.hit.hradar.domain.attendance.query.dto.response.AttendanceDetailResponseDto;
import org.hit.hradar.domain.attendance.query.dto.response.AttendanceListResponseDto;
import org.hit.hradar.domain.attendance.query.service.AttendanceQueryService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Attendance Query", description = "근태 조회 API")
@RestController
@RequestMapping("/attendance")
@RequiredArgsConstructor
public class AttendanceQueryController {

  private final AttendanceQueryService attendanceQueryService;

  // 근태 목록 조회(from~to조회)
  @Operation(summary = "근태 목록 조회", description = "기간별 근태 목록을 조회합니다.")
  @GetMapping
  public List<AttendanceListResponseDto> getAttendanceList(
      @RequestParam(required = false) Long targetEmpId,
      @RequestParam(required = false) Long targetDeptId,
      @RequestParam LocalDate fromDate,
      @RequestParam LocalDate toDate) {
    AttendanceListQueryRequest request = new AttendanceListQueryRequest(
        targetEmpId,
        targetDeptId,
        fromDate,
        toDate);

    return attendanceQueryService.getAttendanceList(request);

  }

  // 근태 상세 조회(하루 단위) 특정사원 + 특정 날짜
  @Operation(summary = "근태 상세 조회", description = "특정 날짜의 근태 상세 정보를 조회합니다.")
  @GetMapping("/detail")
  public AttendanceDetailResponseDto getAttendanceDetail(
      @CurrentUser AuthUser authUser,
      @RequestParam Long targetEmpId,
      @RequestParam LocalDate workDate) {
    AttendanceDetailQueryRequest request = new AttendanceDetailQueryRequest(
        authUser.userId(),
        targetEmpId,
        workDate);

    return attendanceQueryService.getAttendanceDetail(request);
  }

  @Operation(summary = "근태 캘린더 조회", description = "캘린더 형식으로 근태 정보를 조회합니다.")
  @GetMapping("/calendar")
  public List<AttendanceCalendarItemDto> getAttedanceCalendar(
      @RequestParam(required = false) Long targetEmpId,
      @RequestParam(required = false) Long targetDeptId,
      @RequestParam LocalDate fromDate,
      @RequestParam LocalDate toDate) {
    AttendanceListQueryRequest request = new AttendanceListQueryRequest(
        targetEmpId,
        targetDeptId,
        fromDate,
        toDate);

    return attendanceQueryService.getAttendanceCalendar(request);
  }

}