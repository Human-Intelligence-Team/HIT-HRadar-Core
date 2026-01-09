package org.hit.hradar.domain.attendance.command.domain.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.hit.hradar.domain.attendance.command.domain.aggregate.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

  /*
  * 사원의 '하루 근태 상태(attendance)'를 조회하고 저장한다.
  * 출근/퇴근/정정/로그 판단을 위한 기준 데이터를 제공한다
  * 상태 변경 로직은 절대 포함하지 않는다.
  *
  * [핵심 원칙]
  * attendance는 사원 + 근무일 기준으로 하루 1건만 존재한다.
  * 논리삭제(is_deleted='n')조건을 항상 고려한다.*/

  //========================================================================


  //당일 근태 조회(출/퇴근 판단용)
  Optional<Attendance> findByEmpIdAndWorkDate(
      Long empId,
      LocalDate workDate
  );

  //당일 근태 존재 여부 확인(중복 출근 방지)
  boolean existsByEmpIdAndWorkDate(
      Long empId,
      LocalDate workDate
  );

  //사원의 최근 근태 1건 조회(상태)
  Optional<Attendance> findTopByEmpIdOrderByWorkDateDesc(
      Long empId)
  ;

  //사원 근태 목록 조회(부서 근태 조회)
  List<Attendance> findByEmpIdAndWorkDateBetween(
      Long empId,
      LocalDate startDate,
      LocalDate endDate
  );



}
