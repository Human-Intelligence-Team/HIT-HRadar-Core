package org.hit.hradar.domain.employee.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.*;
import org.hit.hradar.global.dto.BaseTimeEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
@Table(name = "employee_account")
public class EmployeeAccount extends BaseTimeEntity {

  @Id
  @Column(name = "emp_account_id", nullable = false)
  private Long empAccountId;

  @Column(name = "com_id", nullable = false)
  private Long comId;

  @Column(name = "emp_id", nullable = false)
  private Long empId;

  @Column(name = "role_id", nullable = false)
  private Long roleId;

  @Column(name = "login_id", nullable = false, length = 50)
  private String loginId;

  @Column(name = "password_hash", nullable = false, length = 50)
  private String passwordHash;

  @Column(name = "is_deleted", nullable = false, length = 1)
  private String isDeleted; // Y/N
}