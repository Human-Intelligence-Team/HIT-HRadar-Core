package org.hit.hradar.domain.attendance.query.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.attendance.command.domain.aggregate.IpPolicyType;
import org.hit.hradar.domain.attendance.command.domain.repository.IpRangePolicyRepository;
import org.hit.hradar.domain.attendance.query.dto.response.IpRangePolicyResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IpRangePolicyQueryService {

  private final IpRangePolicyRepository ipRangePolicyRepository;

  //관리자 회사 전체 IP 정책 목록
  public List<IpRangePolicyResponse> getAll(Long comId) {
    return ipRangePolicyRepository.findByComId(comId)
        .stream()
        .map(IpRangePolicyResponse::from)
        .toList();
  }

  //관리자 활성 IP 정책 목록
  public List<IpRangePolicyResponse> getActive(Long comId) {
    return ipRangePolicyRepository.findByComIdAndIsActiveTrue(comId)
        .stream()
        .map(IpRangePolicyResponse::from)
        .toList();
  }

  //관리자 출퇴근용 IP 정책 목록
  public List<IpRangePolicyResponse> getAttendanceIps(Long comId) {
    return ipRangePolicyRepository
        .findByComIdAndIpPolicyTypeAndIsActiveTrue(comId, IpPolicyType.ATTENDANCE)
        .stream()
        .map(IpRangePolicyResponse::from)
        .toList();
  }
}
