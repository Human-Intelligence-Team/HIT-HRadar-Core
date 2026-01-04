package org.hit.hradar.leave.command.domain.aggregate;

import java.time.LocalDate;

public class LeaveUsage {

  //연차 이력id
  private Integer usageId;

  //연차 사용 기준일
  private LocalDate useDate;

  //차감 연차
  private double usedDays;

  //휴가 id
  private Integer leaveId;
}
