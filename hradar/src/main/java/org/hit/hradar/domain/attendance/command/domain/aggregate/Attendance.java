package org.hit.hradar.domain.attendance.command.domain.aggregate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Getter;
import org.hit.hradar.global.dto.BaseTimeEntity;

@Entity
@Table(name = "attendance")
@Getter
public class Attendance extends BaseTimeEntity {

  //근태id
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "attendance_id")
  private Long attendanceId;

  //사원 id
  @Column(name = "emp_id")
  private Long empId;

  //근무일
  @Column(name = "work_date", nullable = false)
  private LocalDate workDate;

  //근무 유형
  @Enumerated(EnumType.STRING)
  @Column(name = "work_type", nullable = false)
  private WorkType workType = WorkType.WORK;

  //근태 상태
  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private AttendanceStatus status = AttendanceStatus.NORMAL;


  // 생성자

  // 수정자

  //삭제여부
  @Column(name = "is_deleted", nullable = false)
  private Character isDeleted = 'N';
}
