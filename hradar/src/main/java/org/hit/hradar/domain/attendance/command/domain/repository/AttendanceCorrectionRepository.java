package org.hit.hradar.domain.attendance.command.domain.repository;

import org.hit.hradar.domain.attendance.command.domain.aggregate.AttendanceCorrection;

public interface AttendanceCorrectionRepository {

  //근태 정정 신청 저장
  void insertCorrection(AttendanceCorrection correction);

  //정정 ID로 정정 요청 조회
  AttendanceCorrection findByCorrectionId(Long correctionId);

  //정정 상태 변경(승인/반려)
  void updateCorrectionStatus(AttendanceCorrection correction);




}
