package org.hit.hradar.domain.evaluation.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.hit.hradar.domain.evaluation.query.dto.response.EvaluationAssignmentListResponseDto;
import org.hit.hradar.domain.evaluation.query.dto.response.EvaluationAssignmentResponseDto;

import java.util.List;

@Mapper
public interface EvaluationAssignmentMapper {

    List<EvaluationAssignmentResponseDto> selectAssignmentsByEvaluator(
            @Param("evaluatorId") Long evaluatorId
    );

    List<EvaluationAssignmentListResponseDto> selectAssignments(
            @Param("cycleId") Long cycleId,
            @Param("evalTypeCode") String evalTypeCode,
            @Param("status") String status,
            @Param("evaluatorId") Long evaluatorId,
            @Param("evaluateeId") Long evaluateeId
    );
}
