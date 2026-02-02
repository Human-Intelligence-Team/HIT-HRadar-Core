package org.hit.hradar.global.code.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.global.code.query.dto.response.CommonCodeResponseDto;
import org.hit.hradar.global.code.query.service.CommonCodeQueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Common Code Query", description = "시스템 공통 코드 조회 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/common-codes")
public class CommonCodeQueryController {
    private final CommonCodeQueryService commonCodeQueryService;

    // 특정 그룹의 공통 코드 조회
    @Operation(summary = "공통 코드 그룹 목록 조회", description = "시스템에서 사용하는 모든 공통 코드 그룹과 그 하위 코드 목록을 조회합니다.")
    @GetMapping
    public List<CommonCodeResponseDto> getCommonCodes(
            @RequestParam String groupCode) {
        return commonCodeQueryService.getCommonCodes(groupCode);

    }
}
