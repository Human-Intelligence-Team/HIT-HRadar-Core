package org.hit.hradar.domain.attendance.command.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hit.hradar.domain.attendance.command.domain.aggregate.IpPolicyType;

@Getter
@NoArgsConstructor
public class RegisterIpRangePolicyRequest {

  private Long comId;
  private String cidr;
  private String locationName;
  private IpPolicyType type;
}