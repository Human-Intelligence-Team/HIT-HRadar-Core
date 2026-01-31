package org.hit.hradar.domain.grading.query.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.grading.query.dto.response.MyIndividualGradeResponseDto;
import org.hit.hradar.domain.grading.query.mapper.MyIndividualGradeMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly=true)
public class IndividualGradeQueryService {

    private final MyIndividualGradeMapper  myIndividualGradeMapper;

    public MyIndividualGradeResponseDto getMyIndividualGrade(
            Long employeeId,
            Long cycleId
    ){
        MyIndividualGradeResponseDto result =
                myIndividualGradeMapper.findMyIndividualGrade(employeeId, cycleId);

        //등급 미부여 상태
        if (result == null) {
            return new MyIndividualGradeResponseDto(
                    null,
                    null,
                    null,
                    null
            );
        }

        return result;
    }
}
