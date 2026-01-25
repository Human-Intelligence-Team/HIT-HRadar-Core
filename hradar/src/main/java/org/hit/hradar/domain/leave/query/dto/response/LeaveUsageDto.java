package org.hit.hradar.domain.leave.query.dto.response;

import java.time.LocalDate;
import lombok.Getter;

@Getter
public class LeaveUsageDto {

  /** 사용 이력 ID */
  private Long usageId;

  /** 연차 지급 ID */
  private Long grantId;

  /** 사용 날짜 */
  private LocalDate useDate;

  /** 차감 일수 */
  private double usedDays;

}
