package org.hit.hradar.domain.attendance.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.hit.hradar.domain.attendance.command.domain.aggregate.AttendanceCorrection;

@Mapper
public interface AttendanceCorrectionMapper {

  //근태 정정 신청 INSERT
  void insertCorrection(AttendanceCorrection correction);

  //정정 ID 기준 조회
  AttendanceCorrection selectByCorrectionId(Long correctionId);

  //정정 상태 UPDATE (승인 / 반려)
  void updateCorrectionStatus(AttendanceCorrection correction);

}
