package org.hit.hradar.domain.attendance.query.dto.response;

import org.hit.hradar.domain.attendance.command.domain.aggregate.IpPolicyType;
import org.hit.hradar.domain.attendance.command.domain.aggregate.IpRangePolicy;

public record IpRangePolicyResponse(
    Long ipId,
    String cidr,
    String locationName,
    IpPolicyType ipPolicyType,
    boolean isActive
) {
  public static IpRangePolicyResponse from(IpRangePolicy policy) {
    return new IpRangePolicyResponse(
        policy.getIpId(),       //CIDR이 같을때 구분하기위한 정책IpId
        policy.getCidr(),
        policy.getLocationName(),
        policy.getIpPolicyType(),
        policy.isActive()
    );
  }
}
