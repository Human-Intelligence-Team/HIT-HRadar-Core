package org.hit.hradar.domain.attendance.command.domain.policy;

import java.time.LocalDateTime;

//근무 시간 제한 정책(22시 마감)
public interface WorkTimePolicy {

  //현재 시각을 기준으로 강제 종료가 필요한 근무 로그 처리
  void enforce(LocalDateTime now);

}
