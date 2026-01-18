package org.hit.hradar.domain.attendance.command.application.dto.response;

import lombok.Getter;
import org.hit.hradar.domain.attendance.command.domain.aggregate.WorkType;

@Getter
public class AttendanceCheckResponse {

  //현재 근무 유형
  //WORK, OUTSIDE, BUSINESS_TRIP, REMOTE
  private final WorkType workType;

  public AttendanceCheckResponse(WorkType workType) {
    this.workType = workType;
  }



}
