package org.hit.hradar.leave.command.domain.aggregate;

import java.time.LocalDate;

public class LeaveGrant {

  //연차id
  private Integer grantId;

  //연차 적용년도
  private Integer year;

  //총 연차
  private double totalDays;

  //남은 연차
  private double remainingDays;

  //사원id
  private Integer empId;

  //연차 부여된 시각
  private LocalDate grantedAt;
}
