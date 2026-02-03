package org.hit.hradar.global.code.command.application.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.global.code.command.application.dto.request.CreateCommonCodeRequestDto;
import org.hit.hradar.global.code.command.application.dto.request.UpdateCommonCodeRequestDto;
import org.hit.hradar.global.code.command.application.service.CommonCodeCommandService;
import org.hit.hradar.global.code.command.domain.aggregate.CommonCode;
import org.hit.hradar.global.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/common-codes")
public class CommonCodeCommandController {

    private final CommonCodeCommandService commonCodeCommandService;

    @PostMapping
    public ResponseEntity<ApiResponse<String>> createCommonCode(
            @RequestBody CreateCommonCodeRequestDto request
    ){
        commonCodeCommandService.createCommonCode(request);
        return ResponseEntity.ok(ApiResponse.success(null));

    }

    @PutMapping("/{groupCode}/{code}")
    public ResponseEntity<ApiResponse<String>> updateCommonCode(
            @PathVariable String groupCode,
            @PathVariable String code,
            @RequestBody UpdateCommonCodeRequestDto request
    ) {
        commonCodeCommandService.updateCommonCode(groupCode, code, request);
        return ResponseEntity.ok(ApiResponse.success(null));

    }

    @DeleteMapping("/{groupCode}/{code}")
    public ResponseEntity<ApiResponse<String>> deleteCommonCode(
            @PathVariable String groupCode,
            @PathVariable String code
    ) {
        commonCodeCommandService.deleteCommonCode(groupCode, code);
        return ResponseEntity.ok(ApiResponse.success(null));

    }

}
