package org.hit.hradar.domain.attendance.command.domain.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.repository.query.Param;
import org.hit.hradar.domain.attendance.command.domain.aggregate.AttendanceWorkLog;
import org.hit.hradar.domain.attendance.command.domain.aggregate.WorkLogType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AttendanceWorkLogRepository extends JpaRepository<AttendanceWorkLog, Long> {

  //아직 종료되지 않은 정규 근무 로그 조회, logType = REGULAR
  //endAt IS NULL, startAt 날짜 = 오늘, isDeleted = 'N'
  //가독성 + 유지보수성 때문에 JPQL 선택
  @Query("""
    select w
    from AttendanceWorkLog w
    where w.workLogType = :workLogType
      and w.endAt is null
      and w.isDeleted = 'N'
      and function('date', w.startAt) = :workDate
  """)
  List<AttendanceWorkLog> findOpenRegularLogs(
      @Param("workDate") LocalDate workDate,
      @Param("logType")WorkLogType workLogType
  );

  //출/퇴근 판단용 -> 아직 닫히지 않은 로그 1건 조회
  @Query("""
    select w
    from AttendanceWorkLog w
    where w.attendanceId = :attendanceId
      and w.endAt is null
      and w.isDeleted = 'N'
  """)
  AttendanceWorkLog findOpenLog(
      @Param("attendanceId") Long attendanceId
  );
}
