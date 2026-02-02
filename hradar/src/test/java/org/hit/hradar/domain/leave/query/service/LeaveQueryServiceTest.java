package org.hit.hradar.domain.leave.query.service;

import org.hit.hradar.domain.leave.query.dto.response.LeaveDetailDto;
import org.hit.hradar.domain.leave.query.dto.response.LeaveGrantDto;
import org.hit.hradar.domain.leave.query.dto.response.LeaveListDto;
import org.hit.hradar.domain.leave.query.mapper.LeaveDetailMapper;
import org.hit.hradar.domain.leave.query.mapper.LeaveGrantMapper;
import org.hit.hradar.domain.leave.query.mapper.LeaveListMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LeaveQueryServiceTest {

    @InjectMocks
    private LeaveQueryService leaveQueryService;

    @Mock
    private LeaveListMapper leaveListMapper;
    @Mock
    private LeaveDetailMapper leaveDetailMapper;
    @Mock
    private LeaveGrantMapper leaveGrantMapper;

    @Test
    @DisplayName("휴가 목록 조회")
    void getMyLeaveList() {
        Long empId = 1L;
        when(leaveListMapper.findByEmpId(empId)).thenReturn(Collections.emptyList());

        List<LeaveListDto> result = leaveQueryService.getMyLeaveList(empId);

        assertThat(result).isEmpty();
        verify(leaveListMapper).findByEmpId(empId);
    }

    @Test
    @DisplayName("휴가 상세 조회")
    void getLeaveDetail() {
        Long leaveId = 100L;
        LeaveDetailDto detail = new LeaveDetailDto();
        when(leaveDetailMapper.findDetail(leaveId)).thenReturn(detail);

        LeaveDetailDto result = leaveQueryService.getLeaveDetail(leaveId);

        assertThat(result).isEqualTo(detail);
        verify(leaveDetailMapper).findDetail(leaveId);
    }

    @Test
    @DisplayName("연차 부여 내역 조회")
    void getLeafGrant() {
        Long grantId = 10L;
        LeaveGrantDto grant = new LeaveGrantDto();
        when(leaveGrantMapper.findByGrantId(grantId)).thenReturn(grant);

        LeaveGrantDto result = leaveQueryService.getLeaveGrant(grantId);

        assertThat(result).isEqualTo(grant);
        verify(leaveGrantMapper).findByGrantId(grantId);
    }

    @Test
    @DisplayName("내 연차 부여 목록 조회")
    void getMyLeaveGrants() {
        Long empId = 1L;
        when(leaveGrantMapper.findByEmpId(empId)).thenReturn(Collections.emptyList());

        List<LeaveGrantDto> result = leaveQueryService.getMyLeaveGrants(empId);

        assertThat(result).isEmpty();
        verify(leaveGrantMapper).findByEmpId(empId);
    }
}
