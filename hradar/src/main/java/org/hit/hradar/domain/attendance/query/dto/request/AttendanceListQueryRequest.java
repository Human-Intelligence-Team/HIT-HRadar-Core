package org.hit.hradar.domain.attendance.query.dto.request;

import java.time.LocalDate;
import lombok.Getter;

@Getter
public class AttendanceListQueryRequest {

  private Long targetEmpId;
  private Long targetDeptId;
  private LocalDate fromDate;
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