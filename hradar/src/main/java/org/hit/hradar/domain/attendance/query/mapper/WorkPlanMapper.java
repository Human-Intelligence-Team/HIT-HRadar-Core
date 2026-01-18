package org.hit.hradar.domain.attendance.query.mapper;

import java.time.LocalDateTime;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.hit.hradar.domain.attendance.query.dto.WorkPlanDto;

@Mapper
public interface WorkPlanMapper {

  List<WorkPlanDto> findActiveWorkPlans(LocalDateTime now);

}
