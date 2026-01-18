package org.hit.hradar.domain.attendance.query.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.attendance.query.dto.request.AttendanceDetailQueryRequest;
import org.hit.hradar.domain.attendance.query.dto.request.AttendanceListQueryRequest;
import org.hit.hradar.domain.attendance.query.dto.response.AttendanceDetailResponseDto;
import org.hit.hradar.domain.attendance.query.dto.response.AttendanceListResponseDto;
import org.hit.hradar.domain.attendance.query.mapper.AttendanceQueryMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class  AttendanceQueryService {

  private final AttendanceQueryMapper attendanceQueryMapper;

  //근대 목록 조회
  //화면(역할)에 따라 조회 범위만 다름
  public List<AttendanceListResponseDto> getAttendanceList(
      AttendanceListQueryRequest request
  ) {
    return attendanceQueryMapper.findAttendanceList(request);
  }

  //근태 상세 조회(특정사원 + 특정 날짜)
  //화면(역할)에 따라 조회 범위만 다름
  public AttendanceDetailResponseDto getAttendanceDetail (
      AttendanceDetailQueryRequest request
  ) {
    return attendanceQueryMapper.findAttendanceDetail(request);
  }
}