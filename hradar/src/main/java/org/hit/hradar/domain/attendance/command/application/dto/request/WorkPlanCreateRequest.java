package org.hit.hradar.domain.attendance.command.application.dto.request;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class WorkPlanCreateRequest {


  // 근무유형 (REMOTE / TRIP / OUTSIDE)
  private String workType;

  // 근무 장소
  private String location;

  // 근무 시작
  private LocalDateTime startAt;

  // 근무 종료
  private LocalDateTime endAt;

  private Integer overtimeMinutes;


}
