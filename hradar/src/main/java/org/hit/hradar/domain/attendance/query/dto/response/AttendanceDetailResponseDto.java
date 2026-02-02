package org.hit.hradar.domain.attendance.query.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hit.hradar.domain.attendance.command.domain.aggregate.AttendanceStatus;

@Getter
@Setter
@NoArgsConstructor
public class AttendanceDetailResponseDto {

  private Long attendanceId;
  private Long empId;
  private String empName;
  private Long deptId;

  private LocalDate workDate;
  private AttendanceStatus status;
  private String workType;
  private String workPlace;

  private LocalDateTime checkInTime;
  private LocalDateTime checkOutTime;

  private int totalWorkMinutes;

  private List<WorkLogTimelineItemDto> timeline;
}
