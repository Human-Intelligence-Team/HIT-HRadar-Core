package org.hit.hradar.domain.leave.query.dto.response;

import java.time.LocalDate;
import lombok.Getter;

@Getter
public class LeaveDetailDto {

  /** 휴가 ID */
  private Long leaveId;

  /** 사원 ID */
  private Long empId;

  /** 휴가 유형 */
  private String leaveType;

  /** 휴가 단위 */
  private String leaveUnitType;

  /** 휴가 사유 */
  private String reason;

  /** 휴가 시작일시 */
  private LocalDate startDate;

  /** 휴가 종료일시 */
  private LocalDate endDate;

  /** 사용 휴가 일수 */
  private double leaveDays;

}
