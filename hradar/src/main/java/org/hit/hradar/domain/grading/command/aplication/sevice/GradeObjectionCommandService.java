package org.hit.hradar.domain.grading.command.aplication.sevice;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.grading.GradingErrorCode;
import org.hit.hradar.domain.grading.command.aplication.dto.request.RegisterGradeObjectionRequestDto;
import org.hit.hradar.domain.grading.command.aplication.dto.request.ResolveGradeObjectionRequestDto;
import org.hit.hradar.domain.grading.command.domain.aggregate.GradeObjection;
import org.hit.hradar.domain.grading.command.domain.repository.GradeObjectionRepository;
import org.hit.hradar.domain.grading.command.domain.repository.IndividualGradeRepository;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class GradeObjectionCommandService {
    private final GradeObjectionRepository gradeObjectionRepository;
    private final IndividualGradeRepository individualGradeRepository;

    //이의 제기 등록
    public void registerObjection(
            RegisterGradeObjectionRequestDto request
    ) {
        // 개인 등급 존재 확인
        individualGradeRepository.findById(request.getIndividualGradeId())
                .orElseThrow(() ->
                        new BusinessException(
                                GradingErrorCode.INDIVIDUAL_GRADE_NOT_FOUND
                        )
                );

        GradeObjection objection = GradeObjection.builder()
                .individualGradeId(request.getIndividualGradeId())
                .objectionReason(request.getObjectionReason())
                .build();

        gradeObjectionRepository.save(objection);
    }

    //이의제기 승인
    public void acceptObjection(
            Long objectionId,
            ResolveGradeObjectionRequestDto request,
            Long resolverId
    ) {
        GradeObjection objection = getObjection(objectionId);
        objection.accept(request.getResult(), resolverId);
    }

    //이의제기 반려
    public void rejectObjection(
            Long objectionId,
            ResolveGradeObjectionRequestDto request,
            Long resolverId
    ) {
        GradeObjection objection = getObjection(objectionId);
        objection.reject(request.getResult(), resolverId);
    }

    private GradeObjection getObjection(Long id) {
        return gradeObjectionRepository.findById(id)
                .orElseThrow(() ->
                        new BusinessException(
                                GradingErrorCode.GRADE_OBJECTION_NOT_FOUND
                        )
                );
    }

}
