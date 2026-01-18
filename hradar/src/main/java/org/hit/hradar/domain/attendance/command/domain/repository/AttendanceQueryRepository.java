package org.hit.hradar.domain.attendance.command.domain.repository;

import java.time.LocalDate;
import java.util.List;
import org.hit.hradar.domain.attendance.query.dto.response.AttendanceDetailResponseDto;
import org.hit.hradar.domain.attendance.query.dto.response.AttendanceListResponseDto;

public interface AttendanceQueryRepository {

  // 근태 목록(특정 사원의 기간(from ~ to)
  List<AttendanceListResponseDto> findAttendanceList(
      Long targetEmpId,
      LocalDate fromDate,
      LocalDate toDate
  );

  // 근태 상세(특정 사원의 특정 날짜(하루)
  AttendanceDetailResponseDto findAttendanceDetail(
      Long targetEmpId,
      LocalDate workDate
  );
}