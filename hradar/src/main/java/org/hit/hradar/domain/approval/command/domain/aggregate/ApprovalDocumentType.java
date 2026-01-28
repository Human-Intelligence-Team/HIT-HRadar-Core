package org.hit.hradar.domain.approval.command.domain.aggregate;

public enum ApprovalDocumentType {

  ATT_CORRECTION,     // 근태 정정(출퇴근 등)
  WORK_PLAN,
  OVERTIMEMINUTES,   // 초과근무
  LEAVE,             // 휴가
  CANCEL,            // 취소 요청
  SALARY,           //  기본급
  BASIC_SALARY,     // 기본급
  COMPENSATION_SALARY,     // 변동보상
}
