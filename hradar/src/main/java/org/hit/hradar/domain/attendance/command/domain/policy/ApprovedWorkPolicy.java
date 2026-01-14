package org.hit.hradar.domain.attendance.command.domain.policy;

import java.time.LocalDate;
import org.hit.hradar.domain.attendance.command.application.dto.WorkDecision;

//결재 결과를 근태로 해석하는 정책
public interface ApprovedWorkPolicy {

  //특정 사원의 특정 날짜에 승인된 근무유형과 장소를 결정
  WorkDecision resolve(Long empId, LocalDate workDate);

}
