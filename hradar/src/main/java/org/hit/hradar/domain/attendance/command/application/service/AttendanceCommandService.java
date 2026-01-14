package org.hit.hradar.domain.attendance.command.application.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.attendance.AttendanceErrorCode;
import org.hit.hradar.domain.attendance.command.application.dto.WorkDecision;
import org.hit.hradar.domain.attendance.command.domain.aggregate.Attendance;
import org.hit.hradar.domain.attendance.command.domain.aggregate.AttendanceAuthLog;
import org.hit.hradar.domain.attendance.command.domain.aggregate.AttendanceWorkLog;
import org.hit.hradar.domain.attendance.command.domain.aggregate.WorkType;
import org.hit.hradar.domain.attendance.command.domain.policy.ApprovedWorkPolicy;
import org.hit.hradar.domain.attendance.command.domain.policy.IpAccessValidator;
import org.hit.hradar.domain.attendance.command.domain.repository.AttendanceAuthLogRepository;
import org.hit.hradar.domain.attendance.command.domain.repository.AttendanceRepository;
import org.hit.hradar.domain.attendance.command.domain.repository.AttendanceWorkLogRepository;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AttendanceCommandService {

  private final AttendanceRepository attendanceRepository;
  private final AttendanceWorkLogRepository attendanceWorkLogRepository;
  private final AttendanceAuthLogRepository attendanceAuthLogRepository;
  private final ApprovedWorkPolicy approvedWorkPolicy;
  private final IpAccessValidator ipAccessValidator;

  //출퇴근 버튼 클릭 시 호출
  public void checkInOrOut(Long empId, String clientIp) {

    //IP 허용 여부 검증(회사 정책은 IpAccessValidator가 책임)
    if (!isAllowedIp(clientIp)) {
      saveAuthFailLog(clientIp);
      throw new BusinessException(AttendanceErrorCode.INVALID_IP);
    }

    //오늘 근태 조회 또는 생성(하루 1건)
    Attendance attendance = getOrCreateTodayAttendance(empId);

    //승인 완료된 근무유형 + 근무장소 조회
    // - 결재 승인 있으면: 결재값, 없으면 WORK + "OFFICE"
    WorkDecision approvedWork =
        approvedWorkPolicy.resolve(empId, attendance.getWorkDate());

    //근태 엔티티에 근무유형 반영
    attendance.applyWorkType(approvedWork.getWorkType());

    //근무 로그 처리(출근/퇴근 판단 후 WorkLog 처리)
    if (isCheckIn(attendance.getAttendanceId())) {
      // [출근] WorkLog 신규 생성: startAt=now, endAt=null
      createCheckInLog(
          attendance.getAttendanceId(),
          approvedWork.getWorkType(),
          approvedWork.getLocation()
      );
    } else {
      // [퇴근] 열려있는 WorkLog 종료: endAt=now
      closeCheckOutLog(attendance.getAttendanceId());
    }

    //IP 허용 여부 확인(성공 로그 IP 검증 통과 후 실제 출/퇴근 처리 완료)
    saveAuthSuccessLog(attendance.getAttendanceId(), clientIp);
  }

  //IP 허용 여부 판단
  //여기서는 "허용/비허용" 결과만 받는다.
  private boolean isAllowedIp(String clientIp) {
    return ipAccessValidator.isAllowed(clientIp);
  }

  //사원 + 오늘 날짜 기준 Attendance 1건 보장, 없으면 생성 후 저장
  private Attendance getOrCreateTodayAttendance(Long empId) {
    return attendanceRepository
        .findByEmpIdAndWorkDate(empId, LocalDate.now())
        .orElseGet(() ->
            attendanceRepository.save(
                Attendance.create(empId, LocalDate.now())
            )
        );
  }

  //출근/퇴근 판단
  //열려있는(workLog endAt is null) 로그가 없으면 "출근", 열려있는 로그가 있으면 "퇴근"
  private boolean isCheckIn(Long attendanceId) {
    AttendanceWorkLog log =
        attendanceWorkLogRepository.findOpenLog(attendanceId);
    return log == null;
  }

  //출근 로그 생성(location은 승인된 근무장소를 그대로 저장)
  private void createCheckInLog(
      Long attendanceId,
      WorkType workType,
      String location
  ) {
    attendanceWorkLogRepository.save(
        AttendanceWorkLog.createCheckIn(
            attendanceId,
            LocalDateTime.now(),
            workType,
            location
        )
    );
  }

  //퇴근 로그 종료(출근시 열려있는 퇴근로그 닫기 end_at=null인 상태)
  private void closeCheckOutLog(Long attendanceId) {
    AttendanceWorkLog log =
        attendanceWorkLogRepository.findOpenLog(attendanceId);
    log.close(LocalDateTime.now());
  }

  //인증 성공 로그 저장(IP 검증 통과 + 출/퇴근 처리 완료 후 기록)
  private void saveAuthSuccessLog(Long attendanceId, String clientIp) {
    attendanceAuthLogRepository.save(
        AttendanceAuthLog.success(
            attendanceId,
            "IP",
            clientIp
        )
    );
  }

  //인증 실패 로그 저장
  private void saveAuthFailLog(String clientIp) {
    attendanceAuthLogRepository.save(
        AttendanceAuthLog.fail(
            "IP",
            clientIp
        )
    );
  }
}
