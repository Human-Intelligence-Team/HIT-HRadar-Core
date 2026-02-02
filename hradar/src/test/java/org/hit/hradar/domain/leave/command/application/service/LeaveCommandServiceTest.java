package org.hit.hradar.domain.leave.command.application.service;

import org.hit.hradar.domain.leave.LeaveErrorCode;
import org.hit.hradar.domain.leave.command.application.dto.request.LeaveApplyRequest;
import org.hit.hradar.domain.leave.command.domain.aggregate.EmpLeave;
import org.hit.hradar.domain.leave.command.domain.aggregate.LeaveUsage;
import org.hit.hradar.domain.leave.command.infrastructure.EmpLeaveJpaRepository;
import org.hit.hradar.domain.leave.command.infrastructure.LeaveUsageJpaRepository;
import org.hit.hradar.domain.leave.query.dto.response.LeaveGrantDto;
import org.hit.hradar.domain.leave.query.mapper.LeaveGrantMapper;
import org.hit.hradar.domain.leave.query.mapper.LeaveListMapper;
import org.hit.hradar.domain.leave.query.mapper.LeaveUsageMapper;
import org.hit.hradar.global.exception.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LeaveCommandServiceTest {

    @InjectMocks
    private LeaveCommandService leaveCommandService;

    @Mock
    private EmpLeaveJpaRepository empLeaveRepository;
    @Mock
    private LeaveUsageJpaRepository leaveUsageRepository;
    @Mock
    private LeaveListMapper leaveListMapper;
    @Mock
    private LeaveGrantMapper leaveGrantMapper;
    @Mock
    private LeaveUsageMapper leaveUsageMapper;

    @Test
    @DisplayName("휴가 신청 성공: 중복 없고 잔여 연차 충분함")
    void applyLeave_Success() {
        // given
        Long docId = 100L;
        Long employeeId = 10L;
        
        // Mock Request (No Setters available)
        LeaveApplyRequest request = mock(LeaveApplyRequest.class);
        when(request.getGrantId()).thenReturn(1L);
        when(request.getStartDate()).thenReturn(LocalDate.of(2025, 5, 1));
        when(request.getEndDate()).thenReturn(LocalDate.of(2025, 5, 2));
        when(request.getLeaveDays()).thenReturn(2.0);
        when(request.getLeaveType()).thenReturn("ANNUAL");
        when(request.getLeaveUnitType()).thenReturn("DAY");
        when(request.getReason()).thenReturn("Rest");

        // 1. 중복 문서 아님
        when(empLeaveRepository.existsByDocId(docId)).thenReturn(false);

        // 2. 날짜 중복 없음
        when(leaveListMapper.existsOverlap(employeeId, request.getStartDate(), request.getEndDate()))
                .thenReturn(false);

        // 3. 잔여 연차 충분 (잔여 10일)
        LeaveGrantDto grant = mock(LeaveGrantDto.class);
        when(grant.getRemainingDays()).thenReturn(10.0);
        when(leaveGrantMapper.findByGrantId(request.getGrantId())).thenReturn(grant);

        // when
        leaveCommandService.applyLeave(docId, employeeId, request);

        // then
        verify(empLeaveRepository).save(any(EmpLeave.class));
        verify(leaveUsageRepository).save(any(LeaveUsage.class));
        verify(leaveUsageMapper).decreaseRemainingDays(request.getGrantId(), request.getLeaveDays());
    }

    @Test
    @DisplayName("실패: 이미 등록된 문서 ID (중복 처리)")
    void applyLeave_Fail_DuplicateDoc() {
        // given
        when(empLeaveRepository.existsByDocId(any())).thenReturn(true);

        // when & then
        assertThatThrownBy(() -> leaveCommandService.applyLeave(1L, 1L, mock(LeaveApplyRequest.class)))
                .isInstanceOf(BusinessException.class)
                .hasFieldOrPropertyWithValue("errorCode", LeaveErrorCode.LEAVE_ALREADY_APPLIED);
    }

    @Test
    @DisplayName("실패: 휴가 기간 중복")
    void applyLeave_Fail_Overlap() {
        // given
        LeaveApplyRequest request = mock(LeaveApplyRequest.class);
        when(request.getStartDate()).thenReturn(LocalDate.now());
        when(request.getEndDate()).thenReturn(LocalDate.now());

        when(empLeaveRepository.existsByDocId(any())).thenReturn(false);
        when(leaveListMapper.existsOverlap(any(), any(), any())).thenReturn(true);

        // when & then
        assertThatThrownBy(() -> leaveCommandService.applyLeave(1L, 1L, request))
                .isInstanceOf(BusinessException.class)
                .hasFieldOrPropertyWithValue("errorCode", LeaveErrorCode.LEAVE_OVERLAP);
    }

    @Test
    @DisplayName("실패: 잔여 연차 부족")
    void applyLeave_Fail_InsufficientBalance() {
        // given
        LeaveApplyRequest request = mock(LeaveApplyRequest.class);
        when(request.getGrantId()).thenReturn(1L);
        when(request.getLeaveDays()).thenReturn(5.0);

        when(empLeaveRepository.existsByDocId(any())).thenReturn(false);
        when(leaveListMapper.existsOverlap(any(), any(), any())).thenReturn(false);

        LeaveGrantDto grant = mock(LeaveGrantDto.class);
        when(grant.getRemainingDays()).thenReturn(2.0); // 잔여 2일 < 5일
        when(leaveGrantMapper.findByGrantId(1L)).thenReturn(grant);

        // when & then
        assertThatThrownBy(() -> leaveCommandService.applyLeave(1L, 1L, request))
                .isInstanceOf(BusinessException.class)
                .hasFieldOrPropertyWithValue("errorCode", LeaveErrorCode.LEAVE_NOT_ENOUGH);
    }
}
