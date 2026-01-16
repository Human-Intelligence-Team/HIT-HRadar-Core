package org.hit.hradar.global.code.command.application.dto.request;

import lombok.Getter;

@Getter
public class UpdateCodeGroupRequestDto {

    //그룹코드는 수정 막아둠
    private String groupName;
    private String description;
}
