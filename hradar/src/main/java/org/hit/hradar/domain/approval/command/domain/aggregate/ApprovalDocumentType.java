package org.hit.hradar.domain.approval.command.domain.aggregate;

public enum ApprovalDocumentType {

  ATT_CORR,   // 출퇴근 정정
  OVERTIME,   // 초과근무
  REMOTE,     // 재택근무
  TRIP,       // 출장
  LEAVE,      // 휴가
  CANCEL      // 취소(회수/취소 요청용)
}
