package org.hit.hradar.domain.evaluation.query.dto.response;

import lombok.Getter;
import org.hit.hradar.domain.evaluation.command.domain.aggregate.QuestionType;

@Getter
public class QuestionMetaRow {
    private Long questionId;
    private String questionContent;
    private QuestionType questionType;
}
