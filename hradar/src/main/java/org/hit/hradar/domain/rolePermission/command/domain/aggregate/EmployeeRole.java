package org.hit.hradar.domain.rolePermission.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.*;
import org.hit.hradar.global.dto.BaseTimeEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
@Table(
    name = "role_employee",
    uniqueConstraints = {
        @UniqueConstraint(name = "UK_ROLE_EMP", columnNames = {"role_id", "emp_id"})
    }
)
public class EmployeeRole extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "role_emp_id", nullable = false)
  private Long roleEmpId;

  @Column(name = "role_id", nullable = false)
  private Long roleId;

  @Column(name = "emp_id", nullable = false)
  private Long empId;
}
