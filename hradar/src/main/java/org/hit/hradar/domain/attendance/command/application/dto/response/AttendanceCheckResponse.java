package org.hit.hradar.domain.attendance.command.application.dto.response;

import lombok.Getter;

@Getter
public class AttendanceCheckResponse {

  //현재 근무 유형
  //WORK, OUTSIDE, BUSINESS_TRIP, REMOTE
  private final String workType;

  public AttendanceCheckResponse(String workType) {
    this.workType = workType;
  }



}
