package org.hit.hradar.domain.evaluation.query.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.evaluation.query.dto.response.*;
import org.hit.hradar.domain.evaluation.query.mapper.EvaluationResponseMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EvaluationResponseQueryService {

    private final EvaluationResponseMapper evaluationResponseMapper;

    public List<EvaluationResponseResultDto> getResults(Long evalTypeId) {
        List<QuestionMetaRow> questions = evaluationResponseMapper.selectQuestions(evalTypeId);
        List<RatingStatRow> ratingStats = evaluationResponseMapper.selectRatingStats(evalTypeId);
        List<ObjectiveStatRow> objectiveStats = evaluationResponseMapper.selectObjectiveStats(evalTypeId);
        List<SubjectiveAnswerRow> subjectiveRows = evaluationResponseMapper.selectSubjectiveAnswers(evalTypeId);

        Map<Long, EvaluationResponseResultDto> resultMap = new LinkedHashMap<>();

        //기본 문항 생성
        for (QuestionMetaRow q : questions) {
            resultMap.put(
                    q.getQuestionId(),
                    new EvaluationResponseResultDto(
                            q.getQuestionId(),
                            q.getQuestionContent(),
                            q.getQuestionType(),
                            null,
                            null,
                            new ArrayList<>(),
                            new ArrayList<>(),
                            new ArrayList<>()
                    )
            );
        }

        //Rating 처리
        for (RatingStatRow r : ratingStats) {
            EvaluationResponseResultDto dto = resultMap.get(r.getQuestionId());
            dto.getScoreDistributions()
                    .add(new ScoreDistributionDto(r.getScore(), r.getCount()));
        }

        ratingStats.stream()
                .collect(Collectors.groupingBy(RatingStatRow::getQuestionId))
                .forEach((qid, list) ->
                        resultMap.get(qid)
                                .setAverageScore(list.get(0).getAverageScore())
                );

        //Objective처리
        objectiveStats.stream()
                .collect(Collectors.groupingBy(ObjectiveStatRow::getQuestionId))
                .forEach((qid, list) -> {
                    EvaluationResponseResultDto dto = resultMap.get(qid);
                    list.forEach(o ->
                            dto.getOptionDistributions()
                                    .add(new OptionDistributionDto(
                                            o.getOptionId(),
                                            o.getOptionContent(),
                                            o.getCount()
                                    ))
                    );

                    // 가장 많이 선택된 옵션
                    dto.setMostSelectedOption(
                            list.stream()
                                    .max(Comparator.comparingLong(ObjectiveStatRow::getCount))
                                    .map(ObjectiveStatRow::getOptionContent)
                                    .orElse(null)
                    );
                });

        // SUBJECTIVE 처리
        subjectiveRows.forEach(r ->
                resultMap.get(r.getQuestionId())
                        .getSubjectiveAnswers()
                        .add(r.getResponseText())
        );

        return new ArrayList<>(resultMap.values());
    }
}
