package org.hit.hradar.domain.goal.query.dto.response;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OkrProgressResponseDto {

    private Long goalId;

    // OKR = KR 평균
    private BigDecimal progressRate;

    private List<KrProgressResponseDto> keyResults = new ArrayList<>();
}
