package org.hit.hradar.domain.attendance.query.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.attendance.command.domain.aggregate.IpPolicyType;
import org.hit.hradar.domain.attendance.command.infrastructure.IpRangePolicyJpaRepository;
import org.hit.hradar.domain.attendance.query.dto.response.IpRangePolicyResponseDto;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IpRangePolicyQueryService {

  private final IpRangePolicyJpaRepository ipRangePolicyRepository;

  //관리자 회사 전체 IP 정책 목록
  public List<IpRangePolicyResponseDto> getAll(Long comId) {
    return ipRangePolicyRepository.findByComId(comId)
        .stream()
        .map(IpRangePolicyResponseDto::from)
        .toList();
  }

  //관리자 활성 IP 정책 목록
  public List<IpRangePolicyResponseDto> getActive(Long comId) {
    return ipRangePolicyRepository.findByComIdAndIsActiveTrue(comId)
        .stream()
        .map(IpRangePolicyResponseDto::from)
        .toList();
  }

  //관리자 출퇴근용 IP 정책 목록
  public List<IpRangePolicyResponseDto> getAttendanceIps(Long comId) {
    return ipRangePolicyRepository
        .findByComIdAndIpPolicyTypeAndIsActiveTrue(comId, IpPolicyType.ATTENDANCE)
        .stream()
        .map(IpRangePolicyResponseDto::from)
        .toList();
  }
}
