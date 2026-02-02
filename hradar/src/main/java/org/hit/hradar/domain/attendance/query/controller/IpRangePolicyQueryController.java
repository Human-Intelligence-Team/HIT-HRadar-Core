package org.hit.hradar.domain.attendance.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.attendance.query.dto.response.IpRangePolicyResponseDto;
import org.hit.hradar.domain.attendance.query.service.IpRangePolicyQueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Attendance IP Policy Query", description = "IP 정책 조회 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/ip-policies")
public class IpRangePolicyQueryController {

  private final IpRangePolicyQueryService queryService;

  // 관리자 회사 전체 IP 정책 목록
  @Operation(summary = "전체 IP 정책 목록 조회", description = "회사의 모든 IP 정책 목록을 조회합니다.")
  @GetMapping
  public List<IpRangePolicyResponseDto> getAll(@RequestParam Long comId) {
    return queryService.getAll(comId);
  }

  // 관리자 활성 IP 정책 목록
  @Operation(summary = "활성 IP 정책 목록 조회", description = "현재 활성화된 IP 정책 목록을 조회합니다.")
  @GetMapping("/active")
  public List<IpRangePolicyResponseDto> getActive(@RequestParam Long comId) {
    return queryService.getActive(comId);
  }

  // 관리자 출퇴근용 IP 정책 목록
  @Operation(summary = "출퇴근 전용 IP 정책 목록 조회", description = "출퇴근 체크에 사용되는 IP 정책 목록을 조회합니다.")
  @GetMapping("/attendance")
  public List<IpRangePolicyResponseDto> getAttendance(@RequestParam Long comId) {
    return queryService.getAttendanceIps(comId);
  }
}
