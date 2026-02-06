package org.hit.hradar.domain.leave.query.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class LeaveListDto {

  /** 휴가 ID */
  private Long leaveId;

  /** 휴가 유형 (연차, 병가 등) */
  private String leaveType;

  private Long docId;

  private String reason;

  /** 휴가 단위 (FULL, HALF_AM, HALF_PM 등) */
  private String leaveUnitType;

  /** 휴가 시작일시 */
  private LocalDate startDate;

  /** 휴가 종료일시 */
  private LocalDate endDate;

  /** 사용 휴가 일수 */
  private double leaveDays;

  private LocalDateTime requestedAt;

  private String approvalStatus;

  private String empName; // Added for Department View

  private String departmentName;

}
