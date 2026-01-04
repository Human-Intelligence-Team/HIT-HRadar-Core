package org.hit.hradar.leave.command.domain.aggregate;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Leave {

  //휴가id
  private Integer leaveId;

  //휴가 유형
  private String leaveType;

  //휴가 사유
  private String reason;

  //시작일
  private LocalDate startDate;

  //종료일
  private LocalDate endDate;

  //사용 일수
  private double leaveDays;

  //휴가 상태
  private String status;

  //휴가 신청
  private LocalDateTime requestedAt;

  //휴가 승인
  private LocalDateTime approvedAt;

  //반려 사유
  private String rejectedReason;

  //사원id
  private Integer empId;

}
