package org.hit.hradar.domain.attendance.query.dto.response;

import org.hit.hradar.domain.attendance.command.domain.aggregate.IpPolicyType;
import org.hit.hradar.domain.attendance.command.domain.aggregate.IpRangePolicy;

public record IpRangePolicyResponseDto(
    Long ipId,
    String cidr,
    String locationName,
    IpPolicyType ipPolicyType,
    boolean isActive
) {
  public static IpRangePolicyResponseDto from(IpRangePolicy policy) {
    return new IpRangePolicyResponseDto(
        policy.getIpId(),       //CIDR이 같을때 구분하기위한 정책IpId
        policy.getCidr(),
        policy.getLocationName(),
        policy.getIpPolicyType(),
        policy.isActive()
    );
  }
}
