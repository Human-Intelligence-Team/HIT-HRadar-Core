package org.hit.hradar.global.code.command.application.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.global.code.command.application.dto.request.CreateCodeGroupRequestDto;
import org.hit.hradar.global.code.command.application.dto.request.UpdateCodeGroupRequestDto;
import org.hit.hradar.global.code.command.application.service.CodeGroupCommandService;
import org.hit.hradar.global.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/code-groups")
public class CodeGroupCommandController {

    private final CodeGroupCommandService codeGroupCommandService;

    //코드 그룹 생성
    @PostMapping
    public ResponseEntity<ApiResponse<String>> createCodeGroup(
            @RequestBody CreateCodeGroupRequestDto request
    ){
        codeGroupCommandService.createCodeGroup(request);

        return ResponseEntity.ok(ApiResponse.success(null));
    }

    //코드 그룹 수정
    @PutMapping("/{groupCode}")
    public ResponseEntity<ApiResponse<String>> updateCodeGroup(
            @PathVariable String groupCode,
            @RequestBody UpdateCodeGroupRequestDto request
    ){
        codeGroupCommandService.updateCodeGroup(groupCode, request);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    //코드 그룹 삭제
    @DeleteMapping("/{groupCode}")
    public void deleteCodeGroup(
            @PathVariable String groupCode
    ) {
        codeGroupCommandService.deleteCodeGroup(groupCode);
    }

}
