package org.hit.hradar.domain.evaluation.query.mapper;

import org.hit.hradar.domain.evaluation.query.dto.response.row.JobSatisfactionRow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface JobSatisfactionChartMapper {

    List<JobSatisfactionRow> selectMyJobSatisfactionBars(@Param("evaluateeId") Long evaluateeId);
}
