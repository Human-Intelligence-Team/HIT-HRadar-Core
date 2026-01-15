package org.hit.hradar.domain.attendance.query.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hit.hradar.domain.attendance.command.domain.aggregate.WorkLogType;

//근태 상세 조회용 타임라인 아이템DTO
//하루 근태 상세화면에서 "시간 흐름"을 표현하기 위한 최소 단위
@Getter
@AllArgsConstructor
public class WorkLogTimelineItem {

  //로그 유형 CHECK_IN / CHECK_OUT / ETC
  private WorkLogType workLogType;

  // 로그 발생 시각
  private LocalDateTime startAt;

  //근무 장소(출근/외근/출장 장소, 퇴근시 null 가능)
  private String location;

}