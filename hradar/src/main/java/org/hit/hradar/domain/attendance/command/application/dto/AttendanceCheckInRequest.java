package org.hit.hradar.domain.attendance.command.application.dto;

import lombok.Getter;
import org.hit.hradar.domain.attendance.command.domain.aggregate.WorkType;

@Getter
public class AttendanceCheckInRequest {

  private WorkType workType;

}
