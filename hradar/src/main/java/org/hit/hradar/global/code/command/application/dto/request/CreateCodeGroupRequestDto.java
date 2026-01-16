package org.hit.hradar.global.code.command.application.dto.request;

import lombok.Getter;

@Getter
public class CreateCodeGroupRequestDto {

    private String groupCode;
    private String groupName;
    private String description;
}
