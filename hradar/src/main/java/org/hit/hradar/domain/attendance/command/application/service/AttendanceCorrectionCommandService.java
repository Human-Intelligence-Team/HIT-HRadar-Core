package org.hit.hradar.domain.attendance.command.application.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.attendance.command.application.dto.request.AttendanceCorrectionRequest;
import org.hit.hradar.domain.attendance.command.domain.aggregate.AttendanceCorrection;
import org.hit.hradar.domain.attendance.command.domain.aggregate.CorrectionType;
import org.hit.hradar.domain.attendance.command.domain.repository.AttendanceCorrectionRepository;
import org.hit.hradar.domain.attendance.command.domain.repository.AttendanceWorkLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AttendanceCorrectionCommandService {

  private final AttendanceCorrectionRepository attendanceCorrectionRepository;
  private final AttendanceWorkLogRepository attendanceWorkLogRepository;

  //근태 정정 신청(사원)
  //신청 완료시 REQUESTED

  public void requestCorrection(
      Long reqeusterEmpId,
      AttendanceCorrectionRequest request
  ) {

    AttendanceCorrection correction =
        AttendanceCorrection.create(
            request.getAttendanceId(),
            request.getWorkLogId(),
            reqeusterEmpId,
            request.getCorrectionType(),
            request.getReason(),
            request.getRequestedValue()
        );

    attendanceCorrectionRepository.insertCorrection(correction);
  }


  //정정 승인 결과 반영(결재 시스템에서 승인 완료 후 호출)
  public void applyApprovedCorrection(
      Long correctionId,
      Long deciderEmpId
  ) {
    //정정 요청 조회
    AttendanceCorrection correction =
        attendanceCorrectionRepository.findByCorrectionId(correctionId);

    //정정 상태 승인 처리
    correction.approve(deciderEmpId);
    attendanceCorrectionRepository.updateCorrectionStatus(correction);

    //정정 유형 실제 근태 반영
    if (correction.getCorrectionType() == CorrectionType.TIME_CHANGE) {
      //출퇴근 시간 변경
      attendanceWorkLogRepository.updateWorkLogTime(
          correction.getWorkLogId(),
          correction.getRequestedValue()
      );
    }

    if (correction.getCorrectionType() == CorrectionType.LOCATION_CHANGE)  {
      attendanceWorkLogRepository.updateWorkLogLocation(
          correction.getWorkLogId(),
          correction.getRequestedValue()
      );
    }
  }

  //정정 반려 결과 반영
  public void applyRejectedCorrection(
      Long correctionId,
      Long deciderEmpId,
      String rejectReason
  ) {

    AttendanceCorrection correction =
        attendanceCorrectionRepository.findByCorrectionId(correctionId);

    correction.reject(deciderEmpId, rejectReason);

    attendanceCorrectionRepository.updateCorrectionStatus(correction);
  }

}
