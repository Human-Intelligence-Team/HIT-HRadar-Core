package org.hit.hradar.domain.goal.query.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KrProgressResponseDto {
    private Long keyResultId;
    private Long goalId;

    private String content;

    // 최신 진척률 (0~100)
    private Integer latestProgress;
}
