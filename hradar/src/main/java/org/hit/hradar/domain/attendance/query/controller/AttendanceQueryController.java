package org.hit.hradar.domain.attendance.query.controller;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.attendance.query.dto.request.AttendanceDetailQueryRequest;
import org.hit.hradar.domain.attendance.query.dto.request.AttendanceListQueryRequest;
import org.hit.hradar.domain.attendance.query.dto.response.AttendanceDetailResponseDto;
import org.hit.hradar.domain.attendance.query.dto.response.AttendanceListResponseDto;
import org.hit.hradar.domain.attendance.query.service.AttendanceQueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
public class AttendanceQueryController {

  private final AttendanceQueryService attendanceQueryService;

  //근태 목록 조회(from~to조회)
  @GetMapping
  public List<AttendanceListResponseDto> getAttendanceList(
      @RequestParam(required = false) Long targetEmpId,
      @RequestParam(required = false) Long targetDeptId,
      @RequestParam LocalDate fromDate,
      @RequestParam LocalDate toDate
  ) {
    AttendanceListQueryRequest request =
        new AttendanceListQueryRequest(
            targetEmpId,
            targetDeptId,
            fromDate,
            toDate);

    return attendanceQueryService.getAttendanceList(request);


  }

  //근태 상세 조회(하루 단위) 특정사원 + 특정 날짜
  @GetMapping("/detail")
  public AttendanceDetailResponseDto getAttendanceDetail(
      @RequestParam Long targetEmpId,
      @RequestParam LocalDate workDate
  ) {
    AttendanceDetailQueryRequest request =
        new AttendanceDetailQueryRequest(
            null,
            targetEmpId,
            workDate
        );

    return attendanceQueryService.getAttendanceDetail(request);
  }
}