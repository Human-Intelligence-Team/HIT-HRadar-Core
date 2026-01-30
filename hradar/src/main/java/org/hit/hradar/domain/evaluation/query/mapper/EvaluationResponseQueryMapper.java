package org.hit.hradar.domain.evaluation.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.hit.hradar.domain.evaluation.query.dto.response.EvaluationResponseQueryDto;

import java.util.List;

@Mapper
public interface EvaluationResponseQueryMapper {

    List<EvaluationResponseQueryDto> selectResponsesByAssignmentId(
            @Param("assignmentId") Long assignmentId
    );
}