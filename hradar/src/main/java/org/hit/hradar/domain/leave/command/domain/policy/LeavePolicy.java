package org.hit.hradar.domain.leave.command.domain.policy;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "leave_policy")
public class LeavePolicy {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long policyId;

  private Long companyId;

  private String leaveUnitCode;

  private double days;
}
