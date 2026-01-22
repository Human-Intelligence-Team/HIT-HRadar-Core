package org.hit.hradar.domain.department.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hit.hradar.global.dto.BaseTimeEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "department")
public class Department extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "dept_id", nullable = false)
  private Long deptId;

  @Column(name = "com_id", nullable = false)
  private Long companyId;

  @Column(name = "parent_dept_id")
  private Long parentDeptId;

  @Column(name = "manager_employee_id")
  private Long managerEmpId;

  @Column(name = "dept_name", nullable = false)
  private String deptName;

  @Column(name = "dept_phone")
  private String deptPhone;

  @Column(name = "is_deleted", nullable= false , columnDefinition = "CHAR(1) DEFAULT 'N'")
  private Character isDeleted;

  @Builder
  public Department(Long deptId, Long companyId, Long parentDeptId, Long managerEmpId, String deptName, String deptPhone) {
    this.deptId = deptId;
    this.companyId = companyId;
    this.parentDeptId = parentDeptId;
    this.managerEmpId = managerEmpId;
    this.deptName = deptName;
    this.deptPhone = deptPhone;
    this.isDeleted = 'N';
  }

  public void updateDepartment(String deptName, Long parentDeptId, Long managerEmpId, String deptPhone) {
    this.deptName = deptName;
    this.parentDeptId = parentDeptId;
    this.managerEmpId = managerEmpId;
    this.deptPhone = deptPhone;
  }

  public void markAsDeleted() {
    this.isDeleted = 'Y';
  }
}