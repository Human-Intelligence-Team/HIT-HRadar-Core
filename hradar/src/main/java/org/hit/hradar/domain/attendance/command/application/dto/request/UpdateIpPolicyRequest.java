package org.hit.hradar.domain.attendance.command.application.dto.request;

import lombok.Getter;
import org.hit.hradar.domain.attendance.command.domain.aggregate.IpPolicyType;

@Getter
public class UpdateIpPolicyRequest {

  private String cidr;
  private String locationName;
  private IpPolicyType ipPolicyType;

}
