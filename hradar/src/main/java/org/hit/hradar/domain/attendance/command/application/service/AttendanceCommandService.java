package org.hit.hradar.domain.attendance.command.application.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.attendance.AttendanceErrorCode;
import org.hit.hradar.domain.attendance.IpPolicyErrorCode;
import org.hit.hradar.domain.attendance.command.application.dto.response.AttendanceCheckResponse;
import org.hit.hradar.domain.attendance.command.domain.aggregate.Attendance;
import org.hit.hradar.domain.attendance.command.domain.aggregate.AttendanceAuthLog;
import org.hit.hradar.domain.attendance.command.domain.aggregate.AttendanceWorkLog;
import org.hit.hradar.domain.attendance.command.domain.aggregate.WorkLogType;
import org.hit.hradar.domain.attendance.command.domain.aggregate.WorkType;
import org.hit.hradar.domain.attendance.command.domain.policy.IpAccessValidator;
import org.hit.hradar.domain.attendance.command.domain.repository.AttendanceRepository;
import org.hit.hradar.domain.attendance.command.infrastructure.AttendanceAuthLogJpaRepository;
import org.hit.hradar.domain.attendance.command.infrastructure.AttendanceWorkLogJpaRepository;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AttendanceCommandService {

  private final AttendanceRepository attendanceRepository;
  private final IpAccessValidator ipAccessValidator;
  private final AttendanceWorkLogJpaRepository attendanceWorkLogJpaRepository;
  private final AttendanceAuthLogJpaRepository attendanceAuthLogJpaRepository;

  // 출퇴근 버튼 클릭 처리
  public AttendanceCheckResponse processAttendance(
      Long empId,
      Long comId,
      String clientIp
  ) {

    // 1. 회사 IP 대역 검증
    if (!ipAccessValidator.validate(comId, clientIp)) {
      throw new BusinessException(IpPolicyErrorCode.IpRange_NOT_FOUND);
    }

    LocalDate today = LocalDate.now();
    LocalDateTime now = LocalDateTime.now(); // ★ now 선언 (퇴근 계산용)

    // 2. 당일 근태 조회 (없으면 생성)
    Attendance attendance = attendanceRepository
        .findByEmpIdAndWorkDate(empId, today)
        .orElseGet(() -> createTodayAttendance(empId, today));

    // 3. 현재 열려 있는 근무 로그 조회
    Optional<AttendanceWorkLog> openedLogOpt =
        attendanceWorkLogJpaRepository
            .findTopByAttendanceIdAndEndAtIsNullOrderByStartAtDesc(
                attendance.getAttendanceId()
            );

    if (openedLogOpt.isEmpty()) {
      // 출근 처리
      AttendanceWorkLog log = new AttendanceWorkLog(
          attendance.getAttendanceId(),
          WorkLogType.CHECK_IN,
          now,
          "OFFICE"
      );
      attendanceWorkLogJpaRepository.save(log);

    } else {
      // 퇴근 처리
      AttendanceWorkLog openedLog = openedLogOpt.get();
      openedLog.close(now);
      attendanceWorkLogJpaRepository.save(openedLog);
    }

    // 4. 출퇴근 인증 로그 기록 (성공 시만)
    attendanceAuthLogJpaRepository.save(
        new AttendanceAuthLog(attendance.getAttendanceId(), clientIp)
    );

    //지금 단계에서는 항상 WORK 반환 (WorkPlan 미연결)
    return new AttendanceCheckResponse(WorkType.WORK);
  }

  // 당일 Attendance 생성
  private Attendance createTodayAttendance(Long empId, LocalDate date) {
    Attendance attendance = new Attendance(empId, date);
    return attendanceRepository.save(attendance);
  }
}
