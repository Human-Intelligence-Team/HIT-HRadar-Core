package org.hit.hradar.domain.attendance.query.service;

import org.hit.hradar.domain.attendance.query.dto.request.AttendanceDetailQueryRequest;
import org.hit.hradar.domain.attendance.query.dto.request.AttendanceListQueryRequest;
import org.hit.hradar.domain.attendance.query.dto.response.AttendanceCalendarItemDto;
import org.hit.hradar.domain.attendance.query.dto.response.AttendanceDetailResponseDto;
import org.hit.hradar.domain.attendance.query.dto.response.AttendanceListResponseDto;
import org.hit.hradar.domain.attendance.query.mapper.AttendanceQueryMapper;
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
class AttendanceQueryServiceTest {

    @InjectMocks
    private AttendanceQueryService attendanceQueryService;

    @Mock
    private AttendanceQueryMapper attendanceQueryMapper;

    @Test
    @DisplayName("근태 목록 조회: Mapper 호출 검증")
    void getAttendanceList() {
        // given
        AttendanceListQueryRequest request = org.mockito.Mockito.mock(AttendanceListQueryRequest.class);
        when(attendanceQueryMapper.findAttendanceList(request))
                .thenReturn(Collections.emptyList());

        // when
        List<AttendanceListResponseDto> result = attendanceQueryService.getAttendanceList(request);

        // then
        assertThat(result).isEmpty();
        verify(attendanceQueryMapper).findAttendanceList(request);
    }

    @Test
    @DisplayName("근태 상세 조회: Mapper 호출 검증")
    void getAttendanceDetail() {
        // given
        AttendanceDetailQueryRequest request = org.mockito.Mockito.mock(AttendanceDetailQueryRequest.class);
        AttendanceDetailResponseDto response = new AttendanceDetailResponseDto();
        when(attendanceQueryMapper.findAttendanceDetail(request))
                .thenReturn(response);

        // when
        AttendanceDetailResponseDto result = attendanceQueryService.getAttendanceDetail(request);

        // then
        assertThat(result).isEqualTo(response);
        verify(attendanceQueryMapper).findAttendanceDetail(request);
    }

    @Test
    @DisplayName("근태 캘린더 조회: Mapper 호출 검증")
    void getAttendanceCalendar() {
        // given
        AttendanceListQueryRequest request = org.mockito.Mockito.mock(AttendanceListQueryRequest.class);
        when(attendanceQueryMapper.findAttendanceCalendar(request))
                .thenReturn(Collections.emptyList());

        // when
        List<AttendanceCalendarItemDto> result = attendanceQueryService.getAttendanceCalendar(request);

        // then
        assertThat(result).isEmpty();
        verify(attendanceQueryMapper).findAttendanceCalendar(request);
    }
}
