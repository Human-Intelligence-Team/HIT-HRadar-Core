package org.hit.hradar.domain.attendance.command.application.scheduler;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hit.hradar.domain.attendance.command.domain.aggregate.Attendance;
import org.hit.hradar.domain.attendance.command.domain.aggregate.AttendanceWorkLog;
import org.hit.hradar.domain.attendance.command.domain.aggregate.WorkLogType;
import org.hit.hradar.domain.attendance.command.domain.repository.AttendanceRepository;
import org.hit.hradar.domain.attendance.command.infrastructure.AttendanceWorkLogJpaRepository;
import org.hit.hradar.domain.attendance.query.dto.WorkPlanDto;
import org.hit.hradar.domain.attendance.query.mapper.WorkPlanMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class AttendanceAutoWorkScheduler {

  private final WorkPlanMapper workPlanMapper;
  private final AttendanceRepository attendanceRepository;
  private final AttendanceWorkLogJpaRepository workLogRepository;

  private static final LocalTime HARD_CUTOFF_TIME = LocalTime.of(22, 0);

  //자동 근무 시작 스케줄러(1분마다)
  //실행출장/외근/재택/초과근무 자동 시작 처리
  @Scheduled(cron = "0 * * * * *")
  @Transactional
  public void applyAutoWorkStart() {

    LocalDateTime now = LocalDateTime.now();
    LocalDate today = now.toLocalDate();
    LocalDateTime hardCutOffAt =
        LocalDateTime.of(today, HARD_CUTOFF_TIME);

    //현재 시각 기준, 시작했거나 끝난 근무계획 전부 조회
    List<WorkPlanDto> plans = workPlanMapper.findActiveWorkPlans(now);

    for (WorkPlanDto plan : plans) {

      //근무시작 보다 이르면 건너뛴다.(아무것도 하지 않음)
      if (now.isBefore(plan.getStartAt())) {
        continue;
      }

      //오늘 Attendance 조회/생성
      Attendance attendance = attendanceRepository
          .findByEmpIdAndWorkDate(plan.getEmpId(), today)
          .orElseGet(() ->
              attendanceRepository.save(
                  new Attendance(plan.getEmpId(), today)
              )
          );

      //현재 열려 있는 근무 로그 조회(출근중)
      AttendanceWorkLog openedLog =
          workLogRepository
              .findTopByAttendanceIdAndEndAtIsNullOrderByStartAtDesc(
                  attendance.getAttendanceId()
              )
              .orElse(null);

      //종료 시간 지남 → 자동 퇴근(22시 이후 자동퇴근)
      LocalDateTime effectiveEndAt =
          plan.getEndAt().isAfter(hardCutOffAt)
              ? hardCutOffAt
              : plan.getEndAt();

      if (!now.isBefore(effectiveEndAt)) {
        // 자동 퇴근


        if (openedLog != null) {
          openedLog.close(effectiveEndAt);
          workLogRepository.save(openedLog);
        }
        continue;
      }
      //출근 안 하고 바로 출장(startAt ≤ now 시점에 자동 출근 + 출장 로그 생성)
      //이미 출근했고, 15시에 외근 시작(15시 되면 기존 로그 닫고 외근으로 전환)
      //이미 해당 근무유형으로 전환됨(아무 것도 안 함 (중복 방지))
      if (openedLog != null) {
        continue;
      }

      // 자동 CHECK_IN 생성
      AttendanceWorkLog autoLog = new AttendanceWorkLog(
          attendance.getAttendanceId(),
          WorkLogType.CHECK_IN,
          now,
          plan.getLocation()
      );

      workLogRepository.save(autoLog);

      String action = "AUTO_CHECK_IN";
      log.info("[AUTO] empId={} type={} action={}",
          plan.getEmpId(),
          plan.getWorkType(),
          action);

    }
  }
}