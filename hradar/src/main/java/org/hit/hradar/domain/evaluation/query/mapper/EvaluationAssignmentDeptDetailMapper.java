package org.hit.hradar.domain.evaluation.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.hit.hradar.domain.evaluation.query.dto.response.DepartmentEvaluationAssignmentDetailResponse;

import java.util.List;

@Mapper
public interface EvaluationAssignmentDeptDetailMapper {
    List<DepartmentEvaluationAssignmentDetailResponse> selectDeptAssignmentDetails(
            @Param("deptId") Long deptId
    );
}
