package org.hit.hradar.domain.leave.query.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.hit.hradar.domain.leave.query.dto.response.LeaveUsageDto;
import org.springframework.data.repository.query.Param;

@Mapper
public interface LeaveUsageMapper {

  List<LeaveUsageDto> findByLeaveId(@Param("leaveId") Long leaveId);

}
