package org.hit.hradar.global.code.query.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.global.code.query.dto.response.CodeGroupResponseDto;
import org.hit.hradar.global.code.query.service.CodeGroupQueryService;
import org.hit.hradar.global.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/code-groups")
public class CodeGroupQueryController {

    private final CodeGroupQueryService codeGroupQueryService;

    // 코드 그룹 전체 조회
    @GetMapping
    public List<CodeGroupResponseDto> getCodeGroups() {

        return codeGroupQueryService.getCodeGroups();

    }

    // 코드 그룹 단건 조회
    @GetMapping("/{groupCode}")
    public CodeGroupResponseDto getCodeGroup(
            @PathVariable String groupCode
    ) {
        return codeGroupQueryService.getCodeGroup(groupCode);
    }
}