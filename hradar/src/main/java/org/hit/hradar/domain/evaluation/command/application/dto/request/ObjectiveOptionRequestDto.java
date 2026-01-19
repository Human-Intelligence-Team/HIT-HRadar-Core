package org.hit.hradar.domain.evaluation.command.application.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ObjectiveOptionRequestDto {
    private String optionContent;
    private Integer optionScore; // 선택: 점수 없으면 null 허용
}
