package org.hit.hradar.domain.evaluation.query.assembler;

import org.hit.hradar.domain.evaluation.command.domain.aggregate.QuestionType;
import org.hit.hradar.domain.evaluation.query.dto.response.*;
import org.hit.hradar.domain.evaluation.query.dto.response.row.*;

import java.util.*;
import java.util.stream.Collectors;

public class EvaluationSheetAssembler {

    private EvaluationSheetAssembler() {
        // 유틸 클래스이므로 생성자 private
    }

    public static List<EvaluationSectionResponse> assemble(
            List<EvaluationSectionRow> sections,
            List<EvaluationQuestionRow> questions,
            List<ObjectiveOptionRow> options
    ) {

        //옵션을 questionId 기준으로 그룹핑
        Map<Long, List<ObjectiveOptionRow>> optionMap =
                options.stream()
                        .collect(Collectors.groupingBy(ObjectiveOptionRow::getQuestionId));

        //질문을 sectionId기준으로 그룹핑
        Map<Long, List<EvaluationQuestionRow>> questionMap =
                questions.stream()
                        .collect(Collectors.groupingBy(EvaluationQuestionRow::getSectionId));

        //섹션단위로 response 조립
        List<EvaluationSectionResponse> result = new ArrayList<>();

        for (EvaluationSectionRow section : sections) {

            List<EvaluationQuestionRow> sectionQuestions =
                    questionMap.getOrDefault(section.getSectionId(), List.of());

            List<EvaluationQuestionResponse> questionResponses = new ArrayList<>();

            for (EvaluationQuestionRow question : sectionQuestions) {

                List<ObjectiveOptionResponse> optionResponses = List.of();

                // 객관식인 경우만 옵션 매핑
                if (question.getQuestionType() == QuestionType.OBJECTIVE) {
                    List<ObjectiveOptionRow> optionRows =
                            optionMap.getOrDefault(question.getQuestionId(), List.of());

                    optionResponses =
                            optionRows.stream()
                                    .map(opt -> new ObjectiveOptionResponse(
                                            opt.getOptionId(),
                                            opt.getOptionContent(),
                                            opt.getOptionScore()
                                    ))
                                    .toList();
                }

                EvaluationQuestionResponse questionResponse =
                        new EvaluationQuestionResponse(
                                question.getQuestionId(),
                                question.getQuestionType(),
                                question.getQuestionContent(),
                                question.getRequiredStatus(),
                                question.getRatingScale(),
                                optionResponses
                        );

                questionResponses.add(questionResponse);
            }

            EvaluationSectionResponse sectionResponse =
                    new EvaluationSectionResponse(
                            section.getSectionId(),
                            section.getSectionTitle(),
                            section.getSectionDescription(),
                            section.getSectionOrder(),
                            questionResponses
                    );

            result.add(sectionResponse);
        }

        return result;
    }
}
