package org.hit.hradar.domain.attendance.command.application.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hit.hradar.domain.attendance.command.domain.aggregate.CorrectionType;

@Getter
@NoArgsConstructor
public class AttendanceCorrectionRequest {

  //정정 대상 근태 ID
  private Long attendanceId;

  //정정 대상 근무 로그 ID
  private Long workLogId;

  //정정 유형(출퇴근시간, 외근, 출장, 재택 등)
  private CorrectionType correctionType;

  //정정 사유
  private String reason;

  //변경 요청 값(시간/유형 등, 문자열로 지정)
  private String requestedValue;


}
