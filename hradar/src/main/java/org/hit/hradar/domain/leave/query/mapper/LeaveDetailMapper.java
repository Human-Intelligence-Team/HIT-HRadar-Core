package org.hit.hradar.domain.leave.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.hit.hradar.domain.leave.query.dto.response.LeaveDetailDto;
import org.springframework.data.repository.query.Param;

@Mapper
public interface LeaveDetailMapper {

  LeaveDetailDto findDetail(@Param("leaveId") Long leaveId);

}
