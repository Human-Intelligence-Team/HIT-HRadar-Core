package org.hit.hradar.domain.grading.query.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.grading.query.dto.response.GradeObjectionResponseDto;
import org.hit.hradar.domain.grading.query.mapper.GradeObjectionMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GradeObjectionQueryService {
    private final GradeObjectionMapper gradeObjectionMapper;

    public List<GradeObjectionResponseDto> getObjectionsByIndividualGrade(
            Long individualGradeId
    ) {
        return gradeObjectionMapper.findByIndividualGradeId(
                individualGradeId
        );
    }
}
