package org.hit.hradar.global.code.query.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hit.hradar.global.code.command.domain.aggregate.CodeGroup;

@Getter
@AllArgsConstructor
public class CodeGroupResponseDto {

    private String groupCode;
    private String groupName;
    private String description;

    public static CodeGroupResponseDto from(CodeGroup codeGroup) {
        return new CodeGroupResponseDto(
                codeGroup.getGroupCode(),
                codeGroup.getGroupName(),
                codeGroup.getDescription()
        );
    }
}

