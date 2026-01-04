package org.hit.hradar.attendance.command.domain.aggregate;

import java.time.LocalDate;

public class Attendance extends BaseEntity{

  //근태id
  private Integer attendanceId;

  //사원 id
  private Integer empId;

  //근무일시
  private LocalDate workDate;

  //근무 유형
  private String workType;

  //근태 상태
  private String status;

}
