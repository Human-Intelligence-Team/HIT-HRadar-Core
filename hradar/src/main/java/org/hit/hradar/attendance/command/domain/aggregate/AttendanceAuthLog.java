package org.hit.hradar.attendance.command.domain.aggregate;

import java.time.LocalDateTime;

public class AttendanceAuthLog {

  //인증로그 id
  private Integer authLogId;

  //인증 여부
  private String authResult;

  //인증 시각
  private LocalDateTime authAt;

  //근태 id
  private Integer attendanceId;

  //접속ip주소
  private String ipAddress;

}
