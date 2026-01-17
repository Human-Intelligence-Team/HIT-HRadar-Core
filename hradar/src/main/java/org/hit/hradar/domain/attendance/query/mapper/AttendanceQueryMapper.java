package org.hit.hradar.domain.attendance.query.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.hit.hradar.domain.attendance.query.dto.request.AttendanceDetailQueryRequest;
import org.hit.hradar.domain.attendance.query.dto.request.AttendanceListQueryRequest;
import org.hit.hradar.domain.attendance.query.dto.response.AttendanceDetailResponseDto;
import org.hit.hradar.domain.attendance.query.dto.response.AttendanceListResponseDto;

@Mapper
public interface AttendanceQueryMapper {

  // 근태 목록 (기간)
  List<AttendanceListResponseDto> findAttendanceList(
     AttendanceListQueryRequest request
  );

  // 근태 상세 (하루)
  AttendanceDetailResponseDto findAttendanceDetail(
    AttendanceDetailQueryRequest request
  );
}
