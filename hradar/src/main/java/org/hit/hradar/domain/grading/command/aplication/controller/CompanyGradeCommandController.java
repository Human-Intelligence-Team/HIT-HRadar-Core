package org.hit.hradar.domain.grading.command.aplication.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.grading.command.aplication.dto.request.RegisterCompanyGradeRequestDto;
import org.hit.hradar.domain.grading.command.aplication.dto.request.UpdateCompanyGradeRequestDto;
import org.hit.hradar.domain.grading.command.aplication.sevice.CompanyGradeCommandService;
import org.hit.hradar.global.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/companies/{companyId}/grades")
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

    //회사 등급 수정
    @PutMapping("/{gradeId}")
    public ResponseEntity<ApiResponse<Void>> updateCompanyGrade(
            @PathVariable Long companyId,
            @PathVariable Long gradeId,
            @RequestBody UpdateCompanyGradeRequestDto dto
    ) {
        companyGradeCommandService.updateCompanyGrade(companyId, gradeId, dto);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    //회사 등급 삭제
    @DeleteMapping("/{gradeId}")
    public ResponseEntity<ApiResponse<Void>> deleteCompanyGrade(
            @PathVariable Long companyId,
            @PathVariable Long gradeId
    ) {
        companyGradeCommandService.deleteCompanyGrade(companyId, gradeId);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
