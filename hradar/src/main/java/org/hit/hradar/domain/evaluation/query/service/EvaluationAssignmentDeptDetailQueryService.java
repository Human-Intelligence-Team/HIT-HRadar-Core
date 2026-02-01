package org.hit.hradar.domain.evaluation.query.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.evaluation.query.dto.response.DepartmentEvaluationAssignmentDetailResponse;
import org.hit.hradar.domain.evaluation.query.mapper.EvaluationAssignmentDeptDetailMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EvaluationAssignmentDeptDetailQueryService {

    private final EvaluationAssignmentDeptDetailMapper mapper;

    public List<DepartmentEvaluationAssignmentDetailResponse> getDeptAssignmentDetails(
            Long deptId
    ) {
        return mapper.selectDeptAssignmentDetails(deptId);
    }
}
