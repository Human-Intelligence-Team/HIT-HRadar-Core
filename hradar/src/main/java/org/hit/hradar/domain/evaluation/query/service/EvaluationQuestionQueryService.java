package org.hit.hradar.domain.evaluation.query.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.evaluation.query.dto.response.EvaluationQuestionFlatRow;
import org.hit.hradar.domain.evaluation.query.dto.response.EvaluationQuestionResponseDto;
import org.hit.hradar.domain.evaluation.query.dto.response.ObjectiveOptionResponseDto;
import org.hit.hradar.domain.evaluation.query.mapper.EvaluationQuestionMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EvaluationQuestionQueryService {

    private final EvaluationQuestionMapper questionMapper;

    public List<EvaluationQuestionResponseDto> getQuestions(Long sectionId) {
        List<EvaluationQuestionFlatRow> rows =
                questionMapper.selectQuestionsBySection(sectionId);

        Map<Long, EvaluationQuestionResponseDto> resultMap = new LinkedHashMap<>();

        for (EvaluationQuestionFlatRow row : rows) {
            EvaluationQuestionResponseDto question =
                    resultMap.computeIfAbsent(
                            row.getQuestionId(),
                            id -> new EvaluationQuestionResponseDto(
                                    row.getQuestionId(),
                                    row.getQuestionType(),
                                    row.getQuestionContent(),
                                    row.getRequiredStatus(),
                                    row.getRatingScale(),
                                    new ArrayList<>()
                            )
                    );
            //객관식 선택지 추가
            if (row.getOptionId() != null) {
                question.getOptions().add(
                        new ObjectiveOptionResponseDto(
                                row.getOptionId(),
                                row.getOptionContent(),
                                row.getOptionScore()
                        )
                );
            }
        }
        return new ArrayList<>(resultMap.values());
    }
}
