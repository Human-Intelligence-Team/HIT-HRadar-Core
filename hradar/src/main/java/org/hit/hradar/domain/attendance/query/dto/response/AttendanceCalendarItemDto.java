package org.hit.hradar.domain.attendance.query.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AttendanceCalendarItemDto {

  private Long attendanceId;
  private Long empId;
  private String empName;
  private LocalDate workDate;
  private String workType;
  private String status;

  private String departmentName;
  private String jobTitle;
  private Long totalWorkMinutes;
  private Integer overtimeMinutes;
  private String location;

  private LocalDateTime checkInTime;
  private LocalDateTime checkOutTime;
}
