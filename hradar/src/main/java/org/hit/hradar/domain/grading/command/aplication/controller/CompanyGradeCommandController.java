package org.hit.hradar.domain.grading.command.aplication.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.grading.command.aplication.dto.request.RegisterCompanyGradeRequestDto;
import org.hit.hradar.domain.grading.command.aplication.sevice.CompanyGradeCommandService;
import org.hit.hradar.global.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/grades/{companyId}")
public class CompanyGradeCommandController {

    private final CompanyGradeCommandService companyGradeCommandService;

    //회사 등급 등록
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> registerCompanyGrade(
            @PathVariable Long companyId,
            @RequestBody RegisterCompanyGradeRequestDto dto
    ){
        companyGradeCommandService.registerCompanyGrade(companyId, dto);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
