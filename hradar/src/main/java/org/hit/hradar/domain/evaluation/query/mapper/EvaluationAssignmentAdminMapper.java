package org.hit.hradar.domain.evaluation.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.hit.hradar.domain.evaluation.query.dto.response.EvaluationAssignmentAdminResponse;

import java.util.List;

@Mapper
public interface EvaluationAssignmentAdminMapper {

    /**
     * 회차 + 평가유형 기준 배정 현황 조회
     */
    List<EvaluationAssignmentAdminResponse> findAssignmentsByCycleEvalType(
            @Param("cycleEvalTypeId") Long cycleEvalTypeId
    );
}