package org.hit.hradar.domain.attendance.query.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AttendanceListResponseDto {

  private LocalDate workDate;

  private Long empId;
  private String empName;
  private String departmentName;

  private String workType;
  private String status;

  private LocalDateTime checkInTime;
  private LocalDateTime checkOutTime;

  private Integer totalWorkMinutes;
}
