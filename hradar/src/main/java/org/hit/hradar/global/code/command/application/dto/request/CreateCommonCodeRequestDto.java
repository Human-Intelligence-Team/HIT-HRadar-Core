package org.hit.hradar.global.code.command.application.dto.request;

import lombok.Getter;
import org.hit.hradar.global.code.command.domain.aggregate.Language;

@Getter
public class CreateCommonCodeRequestDto {

    private String groupCode;
    private String code;
    private String codeName;
    private int order;
    private Language lang;
}
