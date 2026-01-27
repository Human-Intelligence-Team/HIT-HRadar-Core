package org.hit.hradar.domain.leave.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.hit.hradar.domain.leave.query.dto.response.LeaveGrantDto;
import org.springframework.data.repository.query.Param;

@Mapper
public interface LeaveGrantMapper {

  LeaveGrantDto findByGrantId(@Param("grantId") Long grantId);

}
