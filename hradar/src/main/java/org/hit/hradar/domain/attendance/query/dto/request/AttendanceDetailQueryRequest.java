package org.hit.hradar.domain.attendance.query.dto.request;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AttendanceDetailQueryRequest {

  // 조회 주체
  private Long viewerEmpId;

  // 조회 대상 사원
  private Long targetEmpId;

  // 근무 일자
  private LocalDate workDate;
}