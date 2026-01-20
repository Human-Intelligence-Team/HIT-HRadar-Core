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
@Table(name = "employee_movement_history")
public class EmployeeMovementHistory extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "movement_id", nullable = false)
  private Long movementId;

  @Column(name = "emp_id", nullable = false)
  private Long empId;

  @Column(name = "from_position_id")
  private Long fromPositionId;

  @Column(name = "to_position_id")
  private Long toPositionId;

  @Column(name = "from_dept_id")
  private Long fromDeptId;

  @Column(name = "to_dept_id")
  private Long toDeptId;

  @Column(name = "from_role_id", nullable = false)
  private Long fromRoleId;

  @Column(name = "to_role_id", nullable = false)
  private Long toRoleId;

  @Enumerated(EnumType.STRING)
  @Column(name = "movement_type", nullable = false, length = 30)
  private MovementType movementType;

  @Column(name = "event_reason", length = 255)
  private String eventReason;

  @Column(name = "effective_date", nullable = false)
  private LocalDate effectiveDate;
}