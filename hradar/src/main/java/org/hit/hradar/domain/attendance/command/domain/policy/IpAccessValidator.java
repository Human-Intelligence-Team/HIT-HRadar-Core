package org.hit.hradar.domain.attendance.command.domain.policy;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.attendance.command.domain.aggregate.IpPolicyType;
import org.hit.hradar.domain.attendance.command.domain.aggregate.IpRangePolicy;
import org.hit.hradar.domain.attendance.command.domain.repository.IpRangePolicyRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IpAccessValidator {

  //IP 정책 조회용 레포
  private final IpRangePolicyRepository ipRangePolicyRepository;

  //회사 활성 ip 목록 조회
  public boolean isAllowed(String clientIp) {

    //출퇴근용 활성 IP 정책 조회
    List<IpRangePolicy> policies =
        ipRangePolicyRepository.findByIpPolicyTypeAndIsActiveTrue(
            IpPolicyType.ATTENDANCE
        );

    // 하나라도 CIDR 범위에 포함되면 허용
    return policies.stream()
        .anyMatch(policy -> policy.contains(clientIp));
      }
}
