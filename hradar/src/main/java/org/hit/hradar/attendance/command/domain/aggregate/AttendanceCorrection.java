package org.hit.hradar.attendance.command.domain.aggregate;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AttendanceCorrection {

  //근태 정정id
  private Integer attendanceCorrection;

  //근태id
  private Integer attendanceId;

  //정정 유형
  private String correctionType;

  //정정 사유
  private String reason;

  //변경하려는 값(저장시)
  private String requestedValue;

  //신청 상태
  private String status;

  //요청 일자(같은 요청이 여러 개일 경우)
  private LocalDateTime requestedAt;

  //결정 일자
  private LocalDateTime decidedAt;

  //결정자id
  private Integer decidedBy;

}
