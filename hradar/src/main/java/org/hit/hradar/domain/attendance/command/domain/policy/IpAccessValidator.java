package org.hit.hradar.domain.attendance.command.domain.policy;

import java.net.InetAddress;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.attendance.command.domain.aggregate.IpPolicyType;
import org.hit.hradar.domain.attendance.command.domain.aggregate.IpRangePolicy;
import org.hit.hradar.domain.attendance.command.infrastructure.IpRangePolicyJpaRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IpAccessValidator {

  //IP 정책 조회용 레포
  private final IpRangePolicyJpaRepository ipRangePolicyJpaRepository;

  //회사 활성 ip 목록 조회
  public boolean validate(Long comId, String clientIp) {
    List<IpRangePolicy> policies =
        ipRangePolicyJpaRepository
            .findByComIdAndIpPolicyTypeAndIsActiveTrueAndIsDeletedFalse(
                comId, IpPolicyType.ATTENDANCE
            );
    //CIDR에 clientIp 포함 여부 판단
    for (IpRangePolicy policy : policies) {

      //CIDR 범위에 포함되면 출퇴근 허용
      if (inRange(policy.getCidr(), clientIp)) {
        return true;
      }
    }
    //전부 실패하면 false(외부IP)
    return false;
  }

  /*CIDR 범위에 포함되는지 확인
  * CIDR: 192.168.0.0/24
  * IP:   192.168.0.15 -> 포함 */
  private boolean inRange(String cidr, String clientIp) {
    try {
      String[] parts = cidr.split("/");
      InetAddress base = InetAddress.getByName(parts[0]);
      int prefix = Integer.parseInt(parts[1]);

      // prefix 길이만큼 네트워크 마스크 생성
      int mask = prefix == 0 ? 0 : -1 << (32 - prefix);
      int baseInt = toInt(base.getAddress());
      int clientInt = toInt(InetAddress.getByName(clientIp).getAddress());

      // 네트워크 부분 비교
      return (baseInt & mask) == (clientInt & mask);
    } catch (Exception e) {
      return false;
    }
  }

  // IPv4 문자열을 int로 변환
  //192.168.0.1 → int 값
  private int toInt(byte[] b) {
    return ((b[0] & 0xFF) << 24)
        | ((b[1] & 0xFF) << 16)
        | ((b[2] & 0xFF) << 8)
        | (b[3] & 0xFF);
  }

}
