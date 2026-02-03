package org.hit.hradar.domain.evaluation.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.hit.hradar.domain.evaluation.query.dto.response.EvaluationAssignmentResponse;

import java.util.List;

@Mapper
public interface EvaluationAssignmentMapper {

    //평가자 기준 평가 배정 조회
    List<EvaluationAssignmentResponse> findByEvaluatorId(
            @Param("evaluatorId") Long evaluatorId
    );
}
