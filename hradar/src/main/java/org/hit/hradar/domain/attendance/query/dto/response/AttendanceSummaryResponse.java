package org.hit.hradar.domain.attendance.query.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hit.hradar.domain.attendance.command.domain.aggregate.AttendanceStatus;
import org.hit.hradar.domain.attendance.command.domain.aggregate.WorkType;


//근태 목록 조회용 DTO(일 단위 근태 요약 정보
//사원/팀장/인사팀 공통 화면
@Getter
@Builder
@AllArgsConstructor
public class AttendanceSummaryResponse {

  //근무 일자 (YYYY-MM-DD)
  private LocalDate workDate;

  //사원ID
  private Long empId;

  //사원 이름
  private String empName;

  //부서 이름
  private String departmentName;

  //근무 유형
  //WORK / OUTSIDE / BUSINESS_TRIP / REMOTE
  private WorkType workType;

  //근태 상태
  //출근 / 퇴근 / 미출근 / 결근 등
  private AttendanceStatus status;

  //최초 출근 시각 (없으면 null)
  private LocalDateTime checkInTime;

  //최종 퇴근 시각 (없으면 null)
  private LocalDateTime checkOutTime;

  //총 근무 시간 (분 단위)
  //WorkLog 기반으로 계산된 집계 값
  private int totalWorkMinutes;
}
