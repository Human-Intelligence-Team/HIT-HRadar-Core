package org.hit.hradar.domain.role.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
@Table(name = "role_employee_account")
public class RoleEmployeeAccount {

  @Id
  @Column(name = "emp_acc_id", nullable = false)
  private Long empAccRoleId;

  @Column(name = "role_id", nullable = false)
  private Long roleId;

  @Column(name = "emp_account_id", nullable = false)
  private Long empAccountId;
}