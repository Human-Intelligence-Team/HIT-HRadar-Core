package org.hit.hradar.domain.attendance.query.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.attendance.query.dto.response.IpRangePolicyResponseDto;
import org.hit.hradar.domain.attendance.query.service.IpRangePolicyQueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/ip-policies")
public class IpRangePolicyQueryController {

  private final IpRangePolicyQueryService queryService;

  //관리자 회사 전체 IP 정책 목록
  @GetMapping
  public List<IpRangePolicyResponseDto> getAll(@RequestParam Long comId) {
    return queryService.getAll(comId);
  }

  //관리자 활성 IP 정책 목록
  @GetMapping("/active")
  public List<IpRangePolicyResponseDto> getActive(@RequestParam Long comId) {
    return queryService.getActive(comId);
  }

  //관리자 출퇴근용 IP 정책 목록
  @GetMapping("/attendance")
  public List<IpRangePolicyResponseDto> getAttendance(@RequestParam Long comId) {
    return queryService.getAttendanceIps(comId);
  }
}
