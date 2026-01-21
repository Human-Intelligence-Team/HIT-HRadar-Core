package org.hit.hradar.domain.grading.command.aplication.sevice;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.grading.GradingErrorCode;
import org.hit.hradar.domain.grading.command.aplication.dto.request.RegisterGradeDistributionRuleRequestDto;
import org.hit.hradar.domain.grading.command.domain.aggregate.Grade;
import org.hit.hradar.domain.grading.command.domain.aggregate.GradeDistributionRule;
import org.hit.hradar.domain.grading.command.domain.repository.GradeDistributionRuleRepository;
import org.hit.hradar.domain.grading.command.domain.repository.GradeRepository;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class GradeDistributionRuleCommandService {

    private final GradeRepository gradeRepository;
    private final GradeDistributionRuleRepository ruleRepository;

    public void registerDistributionRule(
            Long companyId,
            Long gradeId,
            RegisterGradeDistributionRuleRequestDto request
    ) {
        //등급 존재 여부 확인
        Grade grade = gradeRepository.findById(gradeId)
                .orElseThrow(() ->
                        new BusinessException(GradingErrorCode.GRADE_NOT_FOUND)
                );

        //회사 소속 등급인지 확인
        if (!grade.getCompanyId().equals(companyId)) {
            throw new BusinessException(GradingErrorCode.GRADE_COMPANY_MISMATCH);
        }

        //이미 배분 정책 존재 여부
        if (ruleRepository.existsByGradeIdAndIsDeleted(gradeId, 'N')) {
            throw new BusinessException(
                    GradingErrorCode.DISTRIBUTION_RULE_ALREADY_EXISTS
            );
        }

        //비율 유효성 검증
        if (request.getMinRatio() > request.getMaxRatio() || request.getMinRatio() < 0 || request.getMaxRatio() > 100) {
            throw new BusinessException(
                    GradingErrorCode.INVALID_DISTRIBUTION_RATIO
            );
        }

        List<Grade> grades = gradeRepository
                .findAllByCompanyIdAndIsDeleted(companyId, 'N');

        int totalMaxRatio = grades.stream()
                .map(Grade::getGradeId)
                .map(gradeIdValue ->
                        ruleRepository.findByGradeIdAndIsDeleted(gradeIdValue, 'N')
                )
                .filter(Optional::isPresent)
                .map(Optional::get)
                .mapToInt(GradeDistributionRule::getMaxRatio)
                .sum();

        if (totalMaxRatio + request.getMaxRatio() > 100) {
            throw new BusinessException(
                    GradingErrorCode.EXCEEDS_TOTAL_DISTRIBUTION_RATIO
            );
        }

        //정책 생성
        GradeDistributionRule rule = GradeDistributionRule.builder()
                .gradeId(gradeId)
                .minRatio(request.getMinRatio())
                .maxRatio(request.getMaxRatio())
                .build();

        ruleRepository.save(rule);
    }

    //정책 수정
    public void updateDistributionRule(
            Long companyId,
            Long gradeId,
            RegisterGradeDistributionRuleRequestDto request
    ) {
        Grade grade = gradeRepository.findById(gradeId)
                .orElseThrow(() ->
                        new BusinessException(GradingErrorCode.GRADE_NOT_FOUND)
                );

        if (!grade.getCompanyId().equals(companyId)) {
            throw new BusinessException(GradingErrorCode.GRADE_COMPANY_MISMATCH);
        }

        GradeDistributionRule rule = ruleRepository
                .findByGradeIdAndIsDeleted(gradeId, 'N')
                .orElseThrow(() ->
                        new BusinessException(GradingErrorCode.DISTRIBUTION_RULE_NOT_FOUND)
                );

        // 비율 기본 검증
        if (request.getMinRatio() > request.getMaxRatio()
                || request.getMinRatio() < 0
                || request.getMaxRatio() > 100) {
            throw new BusinessException(
                    GradingErrorCode.INVALID_DISTRIBUTION_RATIO
            );
        }

        // 전체 max 합 검증
        List<Grade> grades = gradeRepository
                .findAllByCompanyIdAndIsDeleted(companyId, 'N');

        int totalMaxRatioExceptSelf = grades.stream()
                .map(Grade::getGradeId)
                .filter(id -> !id.equals(gradeId))
                .map(id ->
                        ruleRepository.findByGradeIdAndIsDeleted(id, 'N')
                )
                .filter(Optional::isPresent)
                .map(Optional::get)
                .mapToInt(GradeDistributionRule::getMaxRatio)
                .sum();

        if (totalMaxRatioExceptSelf + request.getMaxRatio() > 100) {
            throw new BusinessException(
                    GradingErrorCode.EXCEEDS_TOTAL_DISTRIBUTION_RATIO
            );
        }

        // 값 변경
        rule.update(
                request.getMinRatio(),
                request.getMaxRatio()
        );
    }

    //정책 삭제
    public void deleteDistributionRule(
            Long companyId,
            Long gradeId
    ){
        Grade grade = gradeRepository.findById(gradeId)
                .orElseThrow(() ->
                        new BusinessException(GradingErrorCode.GRADE_NOT_FOUND));

        if (!grade.getCompanyId().equals(companyId)) {
            throw new BusinessException(GradingErrorCode.GRADE_COMPANY_MISMATCH);
        }

        GradeDistributionRule rule = ruleRepository
                .findByGradeIdAndIsDeleted(gradeId, 'N')
                .orElseThrow(() ->
                        new BusinessException(GradingErrorCode.DISTRIBUTION_RULE_NOT_FOUND)
                );

        rule.delete();
    }
}
