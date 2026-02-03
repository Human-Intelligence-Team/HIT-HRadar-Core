package org.hit.hradar.domain.attendance.query.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WorkPlanDto {

  private final Long empId;
  private final String workType;
  private final LocalDateTime startAt;
  private final LocalDateTime endAt;
  private final String location;

}
