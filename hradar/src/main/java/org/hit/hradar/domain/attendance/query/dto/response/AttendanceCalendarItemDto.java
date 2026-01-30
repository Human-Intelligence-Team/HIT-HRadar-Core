package org.hit.hradar.domain.attendance.query.dto.response;

import java.time.LocalDate;
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


}
