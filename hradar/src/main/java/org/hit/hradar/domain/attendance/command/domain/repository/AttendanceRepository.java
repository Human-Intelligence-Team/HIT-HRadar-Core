package org.hit.hradar.domain.attendance.command.domain.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.hit.hradar.domain.attendance.command.domain.aggregate.Attendance;

public interface AttendanceRepository {

  /*
  * 사원의 '하루 근태 상태(attendance)'를 조회하고 저장한다.
  * 출근/퇴근/정정/로그 판단을 위한 기준 데이터를 제공한다
  * 상태 변경 로직은 절대 포함하지 않는다.
  *
  * [핵심 원칙]
  * attendance는 사원 + 근무일 기준으로 하루 1건만 존재한다.
.*/

  Attendance save(Attendance attendance);

  //특정 사원 + 기간 근태 목록
  List<Attendance> findByEmpIdAndWorkDateBetween(
      Long empId,
      LocalDate fromDate,
      LocalDate toDate
  );

  //특정사원 + 특정 날짜
  Optional<Attendance> findByEmpIdAndWorkDate(
      Long empId,
      LocalDate workDate
  );
}
