package org.hit.hradar.domain.department.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.*;
import org.hit.hradar.global.dto.BaseTimeEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
@Table(name = "department")
public class Department extends BaseTimeEntity {

  @Id
  @Column(name = "dept_id", nullable = false)
  private Long deptId;

  @Column(name = "com_id", nullable = false)
  private Long comId;

  @Column(name = "parent_dept_id")
  private Long parentDeptId;

  @Column(name = "manager_employee_id")
  private Long managerEmployeeId;

  @Column(name = "dept_name", nullable = false)
  private String deptName;

  @Column(name = "dept_phone", nullable = false)
  private String deptPhone;

  @Column(name = "is_deleted", nullable = false)
  private String isDeleted = "N";

}