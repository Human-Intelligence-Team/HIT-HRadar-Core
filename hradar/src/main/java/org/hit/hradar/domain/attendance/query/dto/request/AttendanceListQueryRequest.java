package org.hit.hradar.domain.attendance.query.dto.request;

import java.time.LocalDate;
import lombok.Getter;

@Getter
public class AttendanceListQueryRequest {

  // 조회 대상 사원 (사원 화면일 경우에만 사용)
  private Long targetEmpId;

  // 조회 대상 부서 (팀장 화면일 경우 사용, 사원은 null)
  private Long targetDeptId;

  //조회 시작일
  private LocalDate fromDate;

  //조회 종료일
  private LocalDate toDate;

  public AttendanceListQueryRequest(
      Long targetEmpId,
      Long targetDeptId,
      LocalDate fromDate,
      LocalDate toDate
  ) {
    this.targetEmpId = targetEmpId;
    this.targetDeptId = targetDeptId;
    this.fromDate = fromDate;
    this.toDate = toDate;
  }
}