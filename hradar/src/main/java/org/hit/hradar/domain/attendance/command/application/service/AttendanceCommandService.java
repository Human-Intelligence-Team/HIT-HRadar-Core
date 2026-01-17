package org.hit.hradar.domain.attendance.command.application.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

//출퇴근 처리 서비스
//회사 IP 대역 검증
//당일 Attendance 조회/생성
//출근 / 퇴근 자동 판단
//사전 결재된 근무 유형 적용 (없으면 WORK)
//근무 로그 생성/종료

@Service
@RequiredArgsConstructor
@Transactional
public class AttendanceCommandService {

  private final AttendanceRepository attendanceRepository;
  private final IpAccessValidator ipAccessValidator;
  private final AttendanceWorkLogJpaRepository attendanceWorkLogJpaRepository;
  private final AttendanceAuthLogJpaRepository attendanceAuthLogJpaRepository;

  //출퇴근 버튼 클릭 처리
  //상태(isWorking)에 따라 출근 / 퇴근 분기
  public AttendanceCheckResponse processAttendance(
      Long empId,
      Long comId,
      String clientIp
  ) {

    //회사 IP대역 검증
    //CIDR에 포함되면 통과
    //전부 실패시 외부 IP로 차단
    if (!ipAccessValidator.validate(comId, clientIp)) {
      throw new BusinessException(IpPolicyErrorCode.IpRange_NOT_FOUND);
    }

    //오늘 날짜
    LocalDate today = LocalDate.now();

    //당일 근태 조회(없으면 생성)
    Attendance attendance = attendanceRepository
        .findByEmpIdAndWorkDate(empId, today)
        .orElseGet(() -> createTodayAttendance(empId, today));

    //현재 근무 중인지 판단(workLog 있으면 근무 중)
    boolean isWorking =
        attendanceWorkLogJpaRepository
            .existsByAttendanceIdAndEndAtIsNull(attendance.getAttendanceId());

    //근무 유형 결정
    //사전 결재 승인 내역기준(없으면 WORK)
    WorkType workType = resolveWorkType(empId, today);

    if (!isWorking) {
      //출근 처리
      AttendanceWorkLog log = new AttendanceWorkLog(
          attendance.getAttendanceId(),
          WorkLogType.CHECK_IN,
          LocalDateTime.now(),
          "OFFICE"
      );
      attendanceWorkLogJpaRepository.save(log);

    } else {

      // 퇴근 처리
      AttendanceWorkLog openedLog =
          attendanceWorkLogJpaRepository
              .findTopByAttendanceIdAndEndAtIsNullOrderByStartAtDesc(
                  attendance.getAttendanceId()
              );

      if (openedLog == null) {
        throw new BusinessException(
            AttendanceErrorCode.ATTENDANCE_NOT_FOUND
        );
      }

      //핵심
      openedLog.close(LocalDateTime.now());
      attendanceWorkLogJpaRepository.save(openedLog);
    }
    //출퇴근 인증 로그 기록(성공시만 저장)
    AttendanceAuthLog log =
        new AttendanceAuthLog(attendance.getAttendanceId(), clientIp);

    attendanceAuthLogJpaRepository.save(log);

    // 프론트에는 근무 유형만 반환
    return new AttendanceCheckResponse(workType);
  }

  //당일 ATTENDANCE 생성
  //사원 _ 날짜 기준으로 하루 1건만 존재
  private Attendance createTodayAttendance(Long empId, LocalDate date) {
    Attendance attendance = new Attendance(
        empId,
        date
    );
    return attendanceRepository.save(attendance);
  }

  //근무 유형 결정
  //외근/출장/재택/초과근무는 반드시 사전 결쟁 승인
  //결재 내역 없으면 WORK
  private  WorkType resolveWorkType(Long empId, LocalDate date) {
    //approvalQueryService 조회 예정
    return WorkType.WORK;
  }
}
