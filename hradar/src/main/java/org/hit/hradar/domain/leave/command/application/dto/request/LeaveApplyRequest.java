package org.hit.hradar.domain.leave.command.application.dto.request;

import java.time.LocalDate;
import lombok.Getter;

@Getter
public class LeaveApplyRequest {
  private Long leaveGrantId;
  private Long leaveTypeId;
  private String leaveUnitType;
  private String reason;
  private LocalDate fromDate;
  private LocalDate toDate;
  private double days;

}
