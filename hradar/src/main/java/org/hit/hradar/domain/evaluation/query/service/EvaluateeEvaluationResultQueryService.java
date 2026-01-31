package org.hit.hradar.domain.evaluation.query.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.evaluation.query.dto.response.*;
import org.hit.hradar.domain.evaluation.query.dto.response.row.EvaluateeResponseRow;
import org.hit.hradar.domain.evaluation.query.mapper.EvaluateeEvaluationResultMapper;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class EvaluateeEvaluationResultQueryService {

    private final EvaluateeEvaluationResultMapper mapper;

    public EvaluateeEvaluationResultResponse getEvaluateeResult(
            Long evaluateeId,
            Long cycleId,
            Long evalTypeId
    ) {
        List<EvaluateeResponseRow> rows =
                mapper.selectEvaluateeResponses(evaluateeId, cycleId, evalTypeId);

        if (rows.isEmpty()) {
            return null;
        }

        EvaluateeResponseRow first = rows.get(0);

        Map<Long, List<EvaluateeResponseRow>> grouped =
                rows.stream().collect(
                        LinkedHashMap::new,
                        (m, r) -> m.computeIfAbsent(r.getQuestionId(), k -> new ArrayList<>()).add(r),
                        Map::putAll
                );

        List<EvaluateeQuestionResponse> questions = new ArrayList<>();

        for (List<EvaluateeResponseRow> group : grouped.values()) {
            EvaluateeResponseRow q = group.get(0);

            List<EvaluateeAnswerResponse> answers = group.stream()
                    .map(r -> new EvaluateeAnswerResponse(
                            r.getScore(),
                            r.getTextAnswer(),
                            r.getOptionContent()
                    ))
                    .toList();

            questions.add(
                    new EvaluateeQuestionResponse(
                            q.getQuestionId(),
                            q.getQuestionType(),
                            q.getQuestionContent(),
                            answers
                    )
            );
        }

        return new EvaluateeEvaluationResultResponse(
                first.getCycleId(),
                first.getCycleName(),
                first.getEvalTypeId(),
                first.getEvalTypeName(),
                questions
        );
    }
}