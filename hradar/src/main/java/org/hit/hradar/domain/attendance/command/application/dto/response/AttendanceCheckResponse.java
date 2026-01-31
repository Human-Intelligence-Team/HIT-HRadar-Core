package org.hit.hradar.domain.attendance.command.application.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class AttendanceCheckResponse {

  private final String workType;               // 근무 유형
  private final LocalDate workDate;             // 근무 날짜
  private final LocalDateTime checkInTime;      // 출근 시각
  private final String workLocation;            // 근무 장소
  private final String overtimeStatus;          // 초과근무 상태
  private final String ipAddress;               // 사용 IP
  private final String attendanceStatusType;    // CHECK_IN / CHECK_OUT

  public AttendanceCheckResponse(
      String workType,
      LocalDate workDate,
      LocalDateTime checkInTime,
      String workLocation,
      String overtimeStatus,
      String ipAddress,
      String attendanceStatusType
  ) {
    this.workType = workType;
    this.workDate = workDate;
    this.checkInTime = checkInTime;
    this.workLocation = workLocation;
    this.overtimeStatus = overtimeStatus;
    this.ipAddress = ipAddress;
    this.attendanceStatusType = attendanceStatusType;
  }
}
