package org.hit.hradar.domain.leave.command.domain.aggregate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hit.hradar.global.dto.BaseTimeEntity;

@Entity
@Table(name = "leave_policy")
@Getter
@Builder
@AllArgsConstructor
public class LeavePolicy extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "policy_id")
  private Long policyId;

  @Column(name = "company_id", nullable = false)
  private Long companyId;

  @Column(name = "type_code", nullable = false)
  private String typeCode;

  @Column(name = "type_name", nullable = false)
  private String typeName;

  @Column(name = "unit_code", nullable = false)
  private String unitCode;

  @Column(name = "unit_days", nullable = false)
  private double unitDays;

  @Column(name = "is_active", nullable = false)
  private Character isActive = 'N';

  protected LeavePolicy() {}
}