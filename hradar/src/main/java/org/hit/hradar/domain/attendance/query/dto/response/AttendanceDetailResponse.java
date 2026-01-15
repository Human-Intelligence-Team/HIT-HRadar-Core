package org.hit.hradar.domain.attendance.query.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.hit.hradar.domain.attendance.command.domain.aggregate.AttendanceStatus;


//근태 상세 조회용 DTO
//특정 사원 + 특정 날짜의 근태 상세 정보
public class AttendanceDetailResponse {

  private Long attendanceId;

  private Long empId;
  private String empName;
  private Long deptId;

  private LocalDate workDate;
  private AttendanceStatus status;

  private LocalDateTime checkInTime;
  private LocalDateTime checkOutTime;

  //총 근무 시간 (분 단위)
  //WorkLog 기반으로 계산된 집계 값
  private int totalWorkMinutes;

  private List<WorkLogTimelineItem> timeline;

  public AttendanceDetailResponse(
      Long attendanceId,
      Long empId,
      String empName,
      Long deptId,
      LocalDate workDate,
      AttendanceStatus status,
      LocalDateTime checkInTime,
      LocalDateTime checkOutTime,
      int totalWorkMinutes,
      List<WorkLogTimelineItem> workLogs
  ) {
    this.attendanceId = attendanceId;
    this.empId = empId;
    this.empName = empName;
    this.deptId = deptId;
    this.workDate = workDate;
    this.status = status;
    this.checkInTime = checkInTime;
    this.checkOutTime = checkOutTime;
    this.totalWorkMinutes = totalWorkMinutes;
  }

}
