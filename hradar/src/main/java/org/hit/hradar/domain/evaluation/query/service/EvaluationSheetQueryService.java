package org.hit.hradar.domain.evaluation.query.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.evaluation.query.assembler.EvaluationSheetAssembler;
import org.hit.hradar.domain.evaluation.query.dto.response.EvaluationSectionResponse;
import org.hit.hradar.domain.evaluation.query.dto.response.row.EvaluationQuestionRow;
import org.hit.hradar.domain.evaluation.query.dto.response.row.EvaluationSectionRow;
import org.hit.hradar.domain.evaluation.query.dto.response.row.ObjectiveOptionRow;
import org.hit.hradar.domain.evaluation.query.mapper.*;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EvaluationSheetQueryService {

    private final CycleEvaluationTypeMapper cycleEvaluationTypeMapper;
    private final EvaluationSectionMapper evaluationSectionMapper;
    private final EvaluationQuestionMapper evaluationQuestionMapper;
    private final ObjectiveOptionMapper objectiveOptionMapper;

    public List<EvaluationSectionResponse> getEvaluationSheet(
            Long cycleId,
            Long evalTypeId
    ) {
        // 1. cycleId + evalTypeId → cycleEvalTypeId 조회
        Long cycleEvalTypeId =
                cycleEvaluationTypeMapper.findCycleEvalTypeId(cycleId, evalTypeId);

        // 2. 섹션 목록 조회
        List<EvaluationSectionRow> sections =
                evaluationSectionMapper.findByCycleEvalTypeId(cycleEvalTypeId);

        // 3. sectionIds 추출
        List<Long> sectionIds =
                sections.stream()
                        .map(EvaluationSectionRow::getSectionId)
                        .toList();

        // 4. 섹션에 속한 문제 목록 조회
        List<EvaluationQuestionRow> questions =
                evaluationQuestionMapper.findBySectionIds(sectionIds);

        // 5. questionIds 추출
        List<Long> questionIds =
                questions.stream()
                        .map(EvaluationQuestionRow::getQuestionId)
                        .toList();

        // 6. 객관식 옵션 조회 (OBJECTIVE 타입만 의미 있음)
        List<ObjectiveOptionRow> options = Collections.emptyList();
        if (!questionIds.isEmpty()) {
            options = objectiveOptionMapper.findByQuestionIds(questionIds);
        }
        // 7. 섹션 → 문제 → 옵션 구조로 조립
        return EvaluationSheetAssembler.assemble(
                sections, questions, options
        );
    }
}
