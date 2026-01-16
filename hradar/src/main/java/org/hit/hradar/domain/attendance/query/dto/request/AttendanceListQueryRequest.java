package org.hit.hradar.domain.attendance.query.dto.request;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AttendanceListQueryRequest {

  //사원 화면일 경우 본인 empId
  private Long targetEmpId;

  //조회 시작일
  private LocalDate fromDate;

  //조회 종료일
  private LocalDate toDate;


}