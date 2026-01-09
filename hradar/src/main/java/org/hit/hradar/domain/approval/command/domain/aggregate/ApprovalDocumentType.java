package org.hit.hradar.domain.approval.command.domain.aggregate;

public enum ApprovalDocumentType {

  ATT_CORRECTION,     // 근태 정정(출퇴근 등)
  OVERTIME,          // 초과근무
  REMOTE,            // 재택근무
  TRIP,              // 출장
  LEAVE,             // 휴가
  CANCEL,            // 취소 요청
  SALARY              // 급여 관련
}
