package org.hit.hradar.domain.employee.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.*;
import org.hit.hradar.global.dto.BaseTimeEntity;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
@Table(name = "employee")
public class Employee extends BaseTimeEntity {

  @Id
  @Column(name = "emp_id", nullable = false, unique = true)
  private Long empId;

  @Column(name = "com_id", nullable = false, unique = true)
  private Long comId;

  @Column(name = "dept_id", nullable = false)
  private Long deptId;

  @Column(name = "position_id")
  private Long positionId;

  @Column(name = "account_id", nullable = false, unique = true)
  private Long accId;

  @Column(name = "employee_no", nullable = false, length = 30, unique = true)
  private String employeeNo;

  @Column(name = "email", length = 255)
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

  @Column(name = "extension_number", length = 11)
  private String extNo;

  @Column(name = "phone_number", length = 11)
  private String phoneNo;

  @Column(name = "note", length = 1000)
  private String note;

  @Enumerated(EnumType.STRING)
  @Column(name = "type", nullable = false)
  private EmploymentType employmentType;

  @Column(name = "is_deleted", nullable = false, length = 1)
  private String isDeleted = "N";
}