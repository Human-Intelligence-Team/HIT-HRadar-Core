package org.hit.hradar.global.code.query.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hit.hradar.global.code.command.domain.aggregate.CommonCode;

@Getter
@AllArgsConstructor
public class CommonCodeResponseDto {

    private String code;
    private String codeName;
    private Integer order;
    private String lang;

    public static CommonCodeResponseDto from(CommonCode commonCode) {
        return new CommonCodeResponseDto(
                commonCode.getCode(),
                commonCode.getCodeName(),
                commonCode.getOrder(),
                commonCode.getLang().name()
        );
    }
}

