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

@Entity
@Table(name = "Attendance")
@Getter
public class Attendance {

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
  @Column(name = "work_type", nullable = false, length = 50)
  private String workType;

  //근태 상태
  @Column(name = "status", nullable = false, length = 50)
  private String status;


  // 생성자

  // 수정자

  //삭제여부
  @Column(name = "is_deleted", nullable = false)
  private String isDeleted;
}
