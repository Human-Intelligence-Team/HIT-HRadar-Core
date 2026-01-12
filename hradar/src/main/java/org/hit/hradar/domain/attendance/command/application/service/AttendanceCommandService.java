package org.hit.hradar.domain.attendance.command.application.service;


import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.attendance.command.domain.aggregate.Attendance;
import org.hit.hradar.domain.attendance.command.domain.aggregate.AttendanceAuthLog;
import org.hit.hradar.domain.attendance.command.domain.aggregate.AttendanceWorkLog;
import org.hit.hradar.domain.attendance.command.domain.policy.IpRangePolicy;
import org.hit.hradar.domain.attendance.command.domain.repository.AttendanceAuthLogRepository;
import org.hit.hradar.domain.attendance.command.domain.repository.AttendanceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AttendanceCommandService {

  private final AttendanceRepository attendanceRepository;
  private final AttendanceAuthLogRepository attendanceAuthLogRepository;
  private final AttendanceWorkLogRepository attendanceWorkLogRepository;
  private final IpRangePolicyRepository ipRangePolicyRepository;
  private final ApprovedWorkPolicy approvedWorkPolicy;

  //출퇴근 버튼 클릭 시 호출
  public void checkInOrOut(Long empId, String clientIp) {

    //IP 허용 여부 검증
    if (!isAllowedIp(clientIp)) {
      saveAuthFailLog(clientIp);
      throw new IllegalStateException("접근 가능한 IP가 아닙니다.");
    }

    //오늘 근태 조회 또는 생성(하루 1건)
    Attendance attendance = getOrCreateTodayAttendance(empId);

    //승인 완료된 근무유형 + 근무장소 조회
    ApprovedWork approvedWork =
        approvedWorkPolicy.resolve(empId, attendance.getWorkDate());

    //근태 엔티티에 근무유형 반영
    attendance.applyWorkType(approvedWork.getWorkType());

    //출근 / 퇴근 판단
    boolean isCheckIn = isCheckIn(attendance.getAttendanceId());

    //근무 로그 처리
    if (isCheckIn) {
      createCheckInLog(
          attendance.getAttendanceId(),
          approvedWork.getWorkType(),
          approvedWork.getLocation()
      );
    } else {
      closeCheckOutLog(attendance.getAttendanceId());
    }

    //IP 허용 여부 확인
    saveAuthSuccessLog(attendance.getAttendanceId(), clientIp);
  }

    private boolean isAllowedIp(String clientIp) {
      //활성화된 IP대역 목록 조회
      //clientIp가 CIDR 범위 안에 포함되는지 판단
      return ipRangePolicyRepository.existsAllowedRange(clientIp);
    }

    //오늘 근태 조회 또는 생성
    private Attendance getOrCreateTodayAttendance(Long empId) {
      return attendanceRepository
          .findByEmpIdAndWorkDate(empId, LocalDate.now())
          .orElseGet(() -> {
              attendanceRepository.save(
                  Attendance.create(empId, LocalDate.now())
              )
        );
    }

    //승인 완료된 근무유형 결정
    private boolean isCheckIn(Long attendanceId) {
      //오늘 근태 workLog 조회, 없으면 출근 null 이면 퇴근
      AttendanceWorkLog log =
          attendanceWorkLogRepository.findOpenLog(attendanceId);

      //출근
      return log == null;
      }

      //출근 로그 생성
      private void createCheckInLog(
          Long attendanceId,
          WorkType workType,
          String location
      ){
        attendanceWorkLogRepository.save(
            AttendanceWorkLog.createCheckIn(
                attendanceId,
                LocalDateTime.now(),
                workType,
                location
            )
        );
      }
    }

      //퇴근 로그 종료
      private void closeCheckOutLog(Long attendanceId) {
        AttendanceWorkLog log =
            attendanceWorkLogRepository.findOpenLog(attendanceId);
        log.close(LocalDateTime.now());
      }

      //인증 성공 로그 저장
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
      private void saveAuthFailLog(String clientIp){
        attendanceAuthLogRepository.save(
            AttendanceAuthLog.fail(
                "IP",
                clientIp
            );
      }
    }















  }







}
