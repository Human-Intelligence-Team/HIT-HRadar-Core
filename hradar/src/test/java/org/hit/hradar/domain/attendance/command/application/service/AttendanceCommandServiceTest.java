package org.hit.hradar.domain.attendance.command.application.service;

import org.hit.hradar.domain.attendance.IpPolicyErrorCode;
import org.hit.hradar.domain.attendance.command.application.dto.response.AttendanceCheckResponse;
import org.hit.hradar.domain.attendance.command.domain.aggregate.*;
import org.hit.hradar.domain.attendance.command.domain.policy.IpAccessValidator;
import org.hit.hradar.domain.attendance.command.domain.repository.AttendanceRepository;
import org.hit.hradar.domain.attendance.command.infrastructure.AttendanceAuthLogJpaRepository;
import org.hit.hradar.domain.attendance.command.infrastructure.AttendanceWorkLogJpaRepository;
import org.hit.hradar.domain.attendance.command.infrastructure.AttendanceWorkPlanJpaRepository;
import org.hit.hradar.global.exception.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AttendanceCommandServiceTest {

    @InjectMocks
    private AttendanceCommandService attendanceService;

    @Mock
    private AttendanceRepository attendanceRepository;
    @Mock
    private IpAccessValidator ipAccessValidator;
    @Mock
    private AttendanceWorkLogJpaRepository workLogRepository;
    @Mock
    private AttendanceAuthLogJpaRepository authLogRepository;
    @Mock
    private AttendanceWorkPlanJpaRepository workPlanRepository;

    @Test
    @DisplayName("출근 성공: 기존 로그 없음 -> CHECK_IN 생성")
    void checkIn_Success() {
        // given
        Long empId = 10L;
        Long comId = 1L;
        String clientIp = "127.0.0.1";
        LocalDate today = LocalDate.now();

        // 1. IP Valid
        when(ipAccessValidator.validate(comId, clientIp)).thenReturn(true);

        // 2. Attendance Exists
        Attendance attendance = new Attendance(empId, today);
        when(attendanceRepository.findByEmpIdAndWorkDate(empId, today))
                .thenReturn(Optional.of(attendance));

        // 3. No WorkPlan
        when(workPlanRepository.findByEmpIdAndStatusAndStartAtLessThanEqualAndEndAtGreaterThanEqual(any(), any(), any(), any()))
                .thenReturn(Optional.empty());

        // 4. No Open Log (Means Check-in)
        when(workLogRepository.findTopByAttendanceIdAndEndAtIsNullOrderByStartAtDesc(any()))
                .thenReturn(Optional.empty());

        // when
        AttendanceCheckResponse response = attendanceService.processAttendance(empId, comId, clientIp);

        // then
        verify(workLogRepository).save(argThat(log -> log.getWorkLogType() == WorkLogType.CHECK_IN));
        assertThat(response.getAttendanceStatusType()).isEqualTo("CHECK_IN");
        verify(authLogRepository).save(any(AttendanceAuthLog.class));
    }

    @Test
    @DisplayName("출근 시 근무계획 반영: 승인된 근무계획이 있으면 해당 타입/장소 사용")
    void checkIn_WithWorkPlan() {
        // given
        Long empId = 10L;
        Long comId = 1L;
        String clientIp = "127.0.0.1";

        when(ipAccessValidator.validate(comId, clientIp)).thenReturn(true);
        when(attendanceRepository.findByEmpIdAndWorkDate(any(), any()))
                .thenReturn(Optional.of(new Attendance(empId, LocalDate.now())));
        when(workLogRepository.findTopByAttendanceIdAndEndAtIsNullOrderByStartAtDesc(any()))
                .thenReturn(Optional.empty());

        // WorkPlan exists (e.g. Remote Work)
        AttendanceWorkPlan plan = mock(AttendanceWorkPlan.class);
        when(plan.getWorkType()).thenReturn("REMOTE");
        when(plan.getLocation()).thenReturn("HOME");
        
        when(workPlanRepository.findByEmpIdAndStatusAndStartAtLessThanEqualAndEndAtGreaterThanEqual(any(), any(), any(), any()))
                .thenReturn(Optional.of(plan));

        // when
        attendanceService.processAttendance(empId, comId, clientIp);

        // then
        verify(workLogRepository).save(argThat(log -> 
            log.getLocation().equals("HOME") && log.getWorkLogType() == WorkLogType.CHECK_IN
        ));
    }

    @Test
    @DisplayName("퇴근 성공: 열린 로그 존재 -> CHECK_OUT 처리")
    void checkOut_Success() {
        // given
        Long empId = 10L;
        Long comId = 1L;
        String clientIp = "127.0.0.1";

        when(ipAccessValidator.validate(comId, clientIp)).thenReturn(true);
        Attendance attendance = new Attendance(empId, LocalDate.now());
        when(attendanceRepository.findByEmpIdAndWorkDate(any(), any()))
                .thenReturn(Optional.of(attendance));
        
        // Open Log Exists
        AttendanceWorkLog openLog = mock(AttendanceWorkLog.class);
        when(openLog.getLocation()).thenReturn("OFFICE");
        when(workLogRepository.findTopByAttendanceIdAndEndAtIsNullOrderByStartAtDesc(any()))
                .thenReturn(Optional.of(openLog));

        // when
        AttendanceCheckResponse response = attendanceService.processAttendance(empId, comId, clientIp);

        // then
        verify(openLog).close(any(LocalDateTime.class)); // Log Closed
        verify(workLogRepository).save(openLog);
        assertThat(response.getAttendanceStatusType()).isEqualTo("CHECK_OUT");
    }

    @Test
    @DisplayName("실패: 유효하지 않은 IP")
    void process_Fail_InvalidIp() {
        // given
        when(ipAccessValidator.validate(any(), any())).thenReturn(false);

        // when & then
        assertThatThrownBy(() -> attendanceService.processAttendance(1L, 1L, "0.0.0.0"))
                .isInstanceOf(BusinessException.class)
                .hasFieldOrPropertyWithValue("errorCode", IpPolicyErrorCode.IpRange_NOT_FOUND);
    }
}
