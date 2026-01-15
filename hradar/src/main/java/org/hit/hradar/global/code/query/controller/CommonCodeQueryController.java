package org.hit.hradar.global.code.query.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.global.code.query.dto.response.CommonCodeResponseDto;
import org.hit.hradar.global.code.query.service.CommonCodeQueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/common-codes")
public class CommonCodeQueryController {
    private final CommonCodeQueryService commonCodeQueryService;

    // 특정 그룹의 공통 코드 조회
    @GetMapping
    public List<CommonCodeResponseDto> getCommonCodes(
            @RequestParam String groupCode
    ) {
        return commonCodeQueryService.getCommonCodes(groupCode);

    }
}
