package org.hit.hradar.domain.attendance.command.domain.policy;

import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.attendance.command.application.dto.WorkDecision;
import org.hit.hradar.domain.attendance.command.domain.aggregate.WorkType;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApprovedWorkPolicyImpl implements ApprovedWorkPolicy {

  //private final WorkApprovalRepository workApprovalRepository;

  @Override
  public WorkDecision resolve(Long empId, LocalDate workDate) {

    // empId + workDate 기준으로 "승인 완료" 결재 조회
    //승인된 결재가 있으면 결재에 정의된 WorkType 반환 -> 결재에 정의된 장소 반환
    //승인된 결재가 없으면 기본값 내근 + OFFICE

    return new WorkDecision(WorkType.WORK, "OFFICE");
  }
}
