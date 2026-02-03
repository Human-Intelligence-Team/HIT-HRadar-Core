package org.hit.hradar.domain.attendance.command.infrastructure;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.attendance.command.domain.aggregate.AttendanceCorrection;
import org.hit.hradar.domain.attendance.command.domain.repository.AttendanceCorrectionRepository;
import org.hit.hradar.domain.attendance.query.mapper.AttendanceCorrectionMapper;
import org.springframework.stereotype.Repository;


//Domain Repository 인터페이스 구현체
//MyBatis Mapper를 감싸는 어댑터
@Repository
@RequiredArgsConstructor
public class AttendanceCorrectionRepositoryAdapter
    implements AttendanceCorrectionRepository {

  private final AttendanceCorrectionMapper mapper;


  //근태 정정 신청 저장(INSERT)
  @Override
  public void insertCorrection(AttendanceCorrection correction)  {
    mapper.insertCorrection(correction);
  }

  //정정 ID 기준 단건 조회
  @Override
  public AttendanceCorrection findByCorrectionId(Long correctionId) {
    return mapper.selectByCorrectionId(correctionId);
  }

  //정정 상태 변경 승인(APPROVED) / 반려(REJECTED)
  //상태, 결정자, 결정시각 갱신
  @Override
  public void updateCorrectionStatus(AttendanceCorrection correction) {
    mapper.updateCorrectionStatus(correction);
  }
}
