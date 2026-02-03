package org.hit.hradar.domain.evaluation.query.dto.response.row;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EvaluateeResponseRow {

    private Long cycleId;
    private String cycleName;

    private Long evalTypeId;
    private String evalTypeName;

    private Long questionId;
    private String questionType;
    private String questionContent;

    private Integer score;
    private String textAnswer;
    private String optionContent;}
