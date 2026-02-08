package org.hit.hradar.domain.employee.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hit.hradar.global.dto.BaseTimeEntity;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "employee", uniqueConstraints = {
    @UniqueConstraint(name = "UK_EMP_COMPANY_EMP_NO", columnNames = { "com_id", "employee_no" }),
    @UniqueConstraint(name = "UK_EMP_COMPANY_EMAIL", columnNames = { "com_id", "email" })
})
public class Employee extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "emp_id", nullable = false)
  private Long empId;

  @Column(name = "com_id", nullable = false)
  private Long comId;

  @Column(name = "dept_id")
  private Long deptId;

  @Column(name = "position_id")
  private Long positionId;

  @Column(name = "name", length = 50, nullable = false)
  private String name;

  @Column(name = "employee_no", length = 100)
  private String employeeNo;

  @Column(name = "email", length = 150)
  private String email;

  @Enumerated(EnumType.STRING)
  @Column(name = "gender")
  private Gender gender;

  @Column(name = "birth", length = 8)
  private String birth;

  @Column(name = "hire_date")
  private LocalDate hireDate;

  @Column(name = "exit_date")
  private LocalDate exitDate;

  @Column(name = "image", length = 255)
  private String image;

  @Column(name = "extension_number", length = 15)
  private String extNo;

  @Column(name = "phone_number", length = 15)
  private String phoneNo;

  @Column(name = "note", length = 1000)
  private String note;

  @Enumerated(EnumType.STRING)
  @Column(name = "type")
  private EmploymentType employmentType;

  @Column(name = "is_deleted", nullable = false, columnDefinition = "CHAR(1) DEFAULT 'N'")
  private Character isDeleted;

  @Builder
  public Employee(Long empId, Long comId, Long deptId, Long positionId, String name, String employeeNo,
      String email, Gender gender, String birth, LocalDate hireDate, LocalDate exitDate, String image,
      String extNo, String phoneNo, String note, EmploymentType employmentType) {
    this.empId = empId;
    this.comId = comId;
    this.deptId = deptId;
    this.positionId = positionId;
    this.name = name;
    this.employeeNo = employeeNo;
    this.email = email;
    this.gender = gender;
    this.birth = birth;
    this.hireDate = hireDate;
    this.exitDate = exitDate;
    this.image = image;
    this.extNo = extNo;
    this.phoneNo = phoneNo;
    this.note = note;
    this.employmentType = employmentType;
    this.isDeleted = 'N';
  }

  public void updateEmployee(String name, String email, Gender gender, String birth, LocalDate hireDate,
      LocalDate exitDate, String image, String extNo, String phoneNo, String note, EmploymentType employmentType) {
    this.name = name;
    this.email = email;
    this.gender = gender;
    this.birth = birth;
    this.hireDate = hireDate;
    this.exitDate = exitDate;
    this.image = image;
    this.extNo = extNo;
    this.phoneNo = phoneNo;
    this.note = note;
    if (employmentType != null) {
      this.employmentType = employmentType;
    }
  }

  public void deletedEmployee() {
    this.isDeleted = 'Y';
    this.employmentType = EmploymentType.RESIGNED;
  }

  public void changeDepartment(Long deptId) {
    // null 허용: 미배정(삭제) 가능
    if (this.deptId != null && this.deptId.equals(deptId))
      return;
    this.deptId = deptId;
  }

  public void changePosition(Long positionId) {
    // null 허용
    if (this.positionId != null && this.positionId.equals(positionId))
      return;
    this.positionId = positionId;
  }

  public void changeEmployeeNo(String employeeNo) {
    // 선택지 B: null/blank 모두 삭제(null 저장)
    if (employeeNo == null || employeeNo.trim().isEmpty()) {
      this.employeeNo = null;
      return;
    }
    String v = employeeNo.trim();
    if (v.equals(this.employeeNo))
      return;
    this.employeeNo = v;
  }

  public void updateProfileImage(String image) {
    this.image = image;
  }

}
