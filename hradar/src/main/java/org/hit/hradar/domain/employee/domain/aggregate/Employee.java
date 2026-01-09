package org.hit.hradar.domain.employee.domain.aggregate;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "EMPLOYEE")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Employee {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "emp_id", nullable = false)
  private Long empId;

  @Column(name = "com_id", nullable = false)
  private Long comId;

  @Column(name = "dept_id", nullable = false)
  private Long deptId;

  @Column(name = "position_id", nullable = false)
  private Long positionId;

  @Column(name = "employee_no", nullable = false, length = 30)
  private String employeeNo;

  @Column(name = "name", nullable = false, length = 50)
  private String name;

  @Column(name = "email")
  private String email;

  @Column(name = "gender", length = 1)
  private String gender; // M/F

  @Column(name = "hire_date")
  private LocalDate hireDate;

  @Column(name = "exit_date")
  private LocalDate exitDate;

  @Column(name = "image", length = 255)
  private String image;

  @Column(name = "cellphone_no", length = 255)
  private String cellphoneNo;

  @Column(name = "telephone_no", length = 255)
  private String telephoneNo;

  @Column(name = "note", length = 255)
  private String note;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @Column(name = "created_by", length = 50)
  private String createdBy;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @Column(name = "updated_by", length = 50)
  private String updatedBy;

  @Column(name = "is_deleted",  nullable = false, length = 1, columnDefinition = "CHAR(1) DEFAULT 'N'")
    private String isDeleted;
}
