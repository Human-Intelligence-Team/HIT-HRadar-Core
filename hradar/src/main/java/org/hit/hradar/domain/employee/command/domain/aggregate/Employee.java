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
  @Column(name = "emp_id", nullable = false)
  private Long empId;

  @Column(name = "com_id", nullable = false)
  private Long comId;

  @Column(name = "dept_id", nullable = false)
  private Long deptId;

  @Column(name = "position_id")
  private Long positionId;

  @Column(name = "employee_no", nullable = false, length = 30)
  private String employeeNo;

  @Column(name = "name", nullable = false, length = 50)
  private String name;

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

  @Column(name = "cellphone_no", length = 11)
  private String cellphoneNo;

  @Column(name = "telephone_no", length = 11)
  private String telephoneNo;

  @Column(name = "note", length = 1000)
  private String note;

  @Column(name = "type", nullable = false)
  private String EmploymentType;

  @Column(name = "is_deleted", nullable = false, length = 1)
  private String isDeleted = "N";
}