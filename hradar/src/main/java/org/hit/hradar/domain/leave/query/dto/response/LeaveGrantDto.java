package org.hit.hradar.domain.leave.query.dto.response;

import java.time.LocalDate;
import lombok.Getter;

@Getter
public class LeaveGrantDto {

  /** 연차 지급 ID */
  private Long grantId;

  /** 사원 ID */
  private Long empId;

  /** 연차 기준 연도 */
  private int year;

  /** 총 지급 연차 */
  private double totalDays;

  /** 잔여 연차 */
  private double remainingDays;

  /** 연차 발생일 */
  private LocalDate grantedDate;

  /** 연차 만료일 */
  private LocalDate expireDate;
}
