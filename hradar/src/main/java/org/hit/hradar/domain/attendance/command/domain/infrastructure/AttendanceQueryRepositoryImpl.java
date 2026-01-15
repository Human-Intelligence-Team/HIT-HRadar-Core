package org.hit.hradar.domain.attendance.command.domain.infrastructure;

import jakarta.persistence.EntityManager;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.attendance.AttendanceErrorCode;
import org.hit.hradar.domain.attendance.command.domain.aggregate.Attendance;
import org.hit.hradar.domain.attendance.command.domain.aggregate.AttendanceWorkLog;
import org.hit.hradar.domain.attendance.command.domain.repository.AttendanceQueryRepository;
import org.hit.hradar.domain.attendance.command.domain.repository.AttendanceRepository;
import org.hit.hradar.domain.attendance.query.dto.response.AttendanceDetailResponse;
import org.hit.hradar.domain.attendance.query.dto.response.AttendanceSummaryResponse;
import org.hit.hradar.domain.attendance.query.dto.response.WorkLogTimelineItem;
import org.hit.hradar.domain.employee.EmployeeErrorCode;
import org.hit.hradar.domain.employee.command.domain.aggregate.Employee;
import org.hit.hradar.domain.employee.command.domain.repository.EmployeeRepository;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AttendanceQueryRepositoryImpl implements AttendanceQueryRepository {

  private final EntityManager em;
  private final AttendanceRepository attendanceRepository;
  private final AttendanceWorkLogJpaRepository attendanceWorkLogJpaRepository;
  private final EmployeeRepository employeeRepository;

  //근태 목록 조회 (기간 단위)
  //특정 사원의 근태를 기간(from~to) 기준으로 조회
  @Override
  public List<AttendanceSummaryResponse> findAttendanceSummaries(
      Long targetEmpId,
      LocalDate fromDate,
      LocalDate toDate
  ) {
    String jpql = """
        select new org.hit.hradar.domain.attendance.query.dto.response.AttendanceSummaryResponse(
        a.workDate,
        a.empId,
        e.name,
        d.deptName,
        a.workType,
        a.status,
        min(w.startAt),
        max(w.endAt),
        coalesce(
          sum(function('timestampdiff', 'MINUTE', w.startAt, w.endAt)), 0
          )
        )
        from Attendance a
        join Employee e on a.empId = e.empId
        join Department d on e.deptId = d.deptId
        left join AttendanceWorkLog w
          on w.attendanceId = a.attendanceId
          and w.isDeleted = 'N'
        where a.empId = :empId
          and a.workDate between :from and :to
        group by
          a.workDate,
          e.empId,
          e.name,
          d.deptName,
          a.workType,
          a.status
        """;

    //목록 화면은 "하루 1줄" 이므로
    //group by attendanceId
    //min(startAt) = 최초 출근
    //max(endAt)   = 최종 퇴근
    //sum(timestampdiff) = 총 근무 시간
  return em.createQuery(jpql, AttendanceSummaryResponse.class)
      .setParameter("empId", targetEmpId)
      .setParameter("from", fromDate)
      .setParameter("to", toDate)
      .getResultList();

  }

  //특정 사원 + 특정 날짜
  //특정 사원 + 특정 날짜
  //"상세 화면"은 시간 흐름이 중요하므로
  //엔티티 조회 후 Java에서 조립
  @Override
  public AttendanceDetailResponse findAttendanceDetail(
      Long targetEmpId,
      LocalDate workDate
  ) {

    //해당 날짜 근태 존재 여부 확인
    Attendance attendance =
        attendanceRepository.findByEmpIdAndWorkDate(targetEmpId, workDate)
            .orElseThrow(() -> new BusinessException(AttendanceErrorCode.ATTENDANCE_NOT_FOUND));

    //사원 정보 조회
    Employee employee =
        employeeRepository.findById(attendance.getEmpId())
            .orElseThrow(() -> new BusinessException(EmployeeErrorCode.EMPLOYEE_ERROR_CODE));

    //하루 근태 로그 조회(시간순 정렬)
    List<AttendanceWorkLog> logs =
        attendanceWorkLogJpaRepository.findByAttendanceIdOrderByStartAtAsc(
            attendance.getAttendanceId()
        );

    //최초 출근 시각
    LocalDateTime checkInTime = logs.stream()
        .map(AttendanceWorkLog::getStartAt)
        .min(LocalDateTime::compareTo)
        .orElse(null);

    //최종 퇴근 시각
    LocalDateTime checkOutTime = logs.stream()
        .map(AttendanceWorkLog::getEndAt)
        .max(LocalDateTime::compareTo)
        .orElse(null);

    //총 근무 시간 계산
    int totalMinutes = logs.stream()
        .filter(l -> l.getEndAt() != null)
        .mapToInt(l ->
            (int) Duration
                .between(l.getStartAt(), l.getEndAt())
                .toMinutes()
        )
        .sum();

    //DTO 조립
    return new AttendanceDetailResponse(
        attendance.getAttendanceId(),
        employee.getEmpId(),
        employee.getName(),
        employee.getDeptId(),
        attendance.getWorkDate(),
        attendance.getStatus(),
        checkInTime,
        checkOutTime,
        totalMinutes,
        logs.stream()
            .map(l -> new WorkLogTimelineItem(
                l.getWorkLogType(),
                l.getStartAt(),
                l.getLocation()
            ))
            .toList()
    );
  }
}