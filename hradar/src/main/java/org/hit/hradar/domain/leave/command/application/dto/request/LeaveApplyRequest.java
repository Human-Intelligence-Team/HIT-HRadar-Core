package org.hit.hradar.domain.leave.command.application.dto.request;

import java.time.LocalDate;
import lombok.Getter;

@Getter
public class LeaveApplyRequest {
  private Long grantId;
  private String leaveType;
  private String leaveUnitType;
  private String reason;
  private LocalDate startDate;
  private LocalDate endDate;
  private double leaveDays;

}
