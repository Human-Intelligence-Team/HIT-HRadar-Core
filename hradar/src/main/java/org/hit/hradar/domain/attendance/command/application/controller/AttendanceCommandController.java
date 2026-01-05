package org.hit.hradar.domain.attendance.command.application.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.attendance.command.application.service.AttendanceCommandService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/attendance")
@RequiredArgsConstructor
public class AttendanceCommandController {

/*  private final AttendanceCommandService attendanceCommandService;

  // 사원의 출근 요청을 처리한다
  // 근무 유형(workType)을 반드시 함께 전달받는다
  // 출근 시각과 근무 유형을 하나의 트랜잭션으로 기록한다
  // 근무 유형 유효성 판단은 서비스/도메인 책임이다

  // 근태 출근 요청
  @PostMapping
  public ResponseEntity<String> CheckIn(
      @RequestBody AttendaceCehckInReqeust reqeust
  ) {
    Long empId = getLoginEmpId();

    attendanceCommandService.checkIn(empId, reqeust.getWorkType());

    return ResponseEntity.ok().body("출근 등록 완료");
  }

  // 근태 퇴근 요청
  @PostMapping("/checkout")
  public ResponseEntity<String> CheckOUt() {

    Long empId = getLoginEmpId();

    attendanceCommandService.checkOut(empId);

    return ResponseEntity.ok().body("퇴근 등록 완료");
  }

  //생성된 근태 정정 요청
  @PostMapping("/{attendanceId}/corrections")
  public ResponseEntity<String> requestCorrections(
      @PathVariable Long attendanceId
  ) {

    attendanceCommandService.reqeustCorrections(attendanceId);

    return ResponseEntity.ok().body("근태 정정 완료");
  }

*/
}
