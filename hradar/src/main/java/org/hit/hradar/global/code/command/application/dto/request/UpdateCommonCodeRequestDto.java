package org.hit.hradar.global.code.command.application.dto.request;

import lombok.Getter;

@Getter
public class UpdateCommonCodeRequestDto {

   //코드 수정 막음
    private String codeName;
    private int order;
}
