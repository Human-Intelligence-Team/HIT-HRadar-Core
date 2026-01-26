package org.hit.hradar.domain.attendance.command.application.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.attendance.IpPolicyErrorCode;
import org.hit.hradar.domain.attendance.command.application.dto.response.AttendanceCheckResponse;
import org.hit.hradar.domain.attendance.command.domain.aggregate.ApprovalStatus;
import org.hit.hradar.domain.attendance.command.domain.aggregate.Attendance;
import org.hit.hradar.domain.attendance.command.domain.aggregate.AttendanceAuthLog;
import org.hit.hradar.domain.attendance.command.domain.aggregate.AttendanceWorkLog;
import org.hit.hradar.domain.attendance.command.domain.aggregate.AttendanceWorkPlan;
import org.hit.hradar.domain.attendance.command.domain.aggregate.WorkLogType;
import org.hit.hradar.domain.attendance.command.domain.policy.IpAccessValidator;
import org.hit.hradar.domain.attendance.command.domain.repository.AttendanceRepository;
import org.hit.hradar.domain.attendance.command.infrastructure.AttendanceAuthLogJpaRepository;
import org.hit.hradar.domain.attendance.command.infrastructure.AttendanceWorkLogJpaRepository;
import org.hit.hradar.domain.attendance.command.infrastructure.AttendanceWorkPlanJpaRepository;
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
  private final AttendanceWorkPlanJpaRepository attendanceWorkPlanJpaRepository;

  // 출퇴근 버튼 클릭 처리
  public AttendanceCheckResponse processAttendance(
      Long empId,
      Long comId,
      String clientIp
  ) {
    System.out.println("1 ip대역 통과");
    // 1. 회사 IP 대역 검증
    if (!ipAccessValidator.validate(comId, clientIp)) {
      throw new BusinessException(IpPolicyErrorCode.IpRange_NOT_FOUND);
    }
    System.out.println("2 ip대역 통과");
    LocalDate today = LocalDate.now();
    LocalDateTime now = LocalDateTime.now();

    System.out.println("3 ip대역 통과");
    // 2. 당일 근태 조회 (없으면 생성)
    Attendance attendance = attendanceRepository
        .findByEmpIdAndWorkDate(empId, today)
        .orElseGet(() -> createTodayAttendance(empId, today));

    System.out.println("4 ip대역 통과");
    LocalDateTime startOfDay = today.atStartOfDay();
    LocalDateTime endOfDay = today.atTime(23, 59, 59);

    System.out.println("5 ip대역 통과");
    Optional<AttendanceWorkPlan> approvedPlanOpt =
        attendanceWorkPlanJpaRepository
            .findByEmpIdAndStatusAndStartAtLessThanEqualAndEndAtGreaterThanEqual(
            empId,
            ApprovalStatus.APPROVED,
            now,
            now
        );

    System.out.println("6 ip대역 통과");
    // 3. 현재 열려 있는 근무 로그 조회
    Optional<AttendanceWorkLog> openedLogOpt =
        attendanceWorkLogJpaRepository
            .findTopByAttendanceIdAndEndAtIsNullOrderByStartAtDesc(
                attendance.getAttendanceId()
            );
    System.out.println("7 ip대역 통과");
    if (openedLogOpt.isEmpty()) {
      // 출근 처리
      System.out.println("8 ip대역 통과");
      AttendanceWorkPlan plan = approvedPlanOpt.orElse(null);

      String location = (plan != null) ? plan.getLocation() : "OFFICE";
      System.out.println("9 ip대역 통과");
      AttendanceWorkLog log = new AttendanceWorkLog(
          attendance.getAttendanceId(),
          WorkLogType.CHECK_IN,
          now,
          location
      ); System.out.println("10 ip대역 통과");
      attendanceWorkLogJpaRepository.save(log);

      if (plan != null) {
        attendance.changeWorkType(plan.getWorkType());

      }

      System.out.println("11 ip대역 통과");
    } else {
      // 퇴근 처리
      AttendanceWorkLog openedLog = openedLogOpt.get();
      openedLog.close(now);
      attendanceWorkLogJpaRepository.save(openedLog);
    }
    System.out.println("12 ip대역 통과");
    // 4. 출퇴근 인증 로그 기록 (성공 시만)
    attendanceAuthLogJpaRepository.save(
        new AttendanceAuthLog(attendance.getAttendanceId(), clientIp)
    );
    System.out.println("13 ip대역 통과");
    // ★ 응답값도 실제 근무유형 기반으로 반환 가능
    return new AttendanceCheckResponse(attendance.getWorkType());
  }

  private Attendance createTodayAttendance(Long empId, LocalDate date) {
    Attendance attendance = new Attendance(empId, date);
    return attendanceRepository.save(attendance);
  }
}
