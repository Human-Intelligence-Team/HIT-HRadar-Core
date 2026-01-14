package org.hit.hradar.domain.attendance.command.application.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import org.hit.hradar.domain.attendance.command.application.service.AttendanceCommandService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
public class AttendanceCommandController {

  private final AttendanceCommandService attendanceCommandService;

  //출퇴근 버튼 클릭 API
  @PostMapping("/check")
  public void checkInOrOut(
      @RequestParam Long empId,
      HttpServletRequest request
      ) {

    // 요청에서 client IP만 추출
    String clientIp = extractClientIp(request);

    //출퇴근 처리
    attendanceCommandService.checkInOrOut(empId, clientIp);
  }

  //실제 클라이언트 IP 추출
  //X-Forwarded-For: 203.0.113.10, 10.0.0.1, 10.0.0.2
  //앞이 실제 사용자 IP
  private String extractClientIp(HttpServletRequest request) {
    String forwarded = request.getHeader("X-Forwarded-For");
    if (forwarded != null && !forwarded.isBlank()) {
      return forwarded.split(",")[0].trim();
    }
    return request.getRemoteAddr();
  }
}
