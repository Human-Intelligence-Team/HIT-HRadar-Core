package org.hit.hradar.domain.evaluation.query.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EvaluationTypeResponseDto {

    private Long evalTypeId; //섹션 조회용
    private String evalTypeCode; //프론트 분기용
}
