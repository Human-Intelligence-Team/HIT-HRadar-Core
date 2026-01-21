package org.hit.hradar.domain.grading.command.aplication.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.grading.command.aplication.dto.request.AssignIndividualGradeRequestDto;
import org.hit.hradar.domain.grading.command.aplication.dto.request.IndividualGradeUpdateRequestDto;
import org.hit.hradar.domain.grading.command.aplication.sevice.IndividualGradeCommandService;
import org.hit.hradar.global.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/individual-grades")
public class IndividualGradeCommandController {

    private final IndividualGradeCommandService  individualGradeCommandService;

    //등록
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> assignIndividualGrade(
            @RequestBody AssignIndividualGradeRequestDto request
    ) {
        individualGradeCommandService.assignIndividualGrade(request);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    //수정
    @PutMapping("/{individualGradeId}")
    public ResponseEntity<ApiResponse<Void>> updateIndividualGrade(
            @PathVariable Long individualGradeId,
            @RequestBody IndividualGradeUpdateRequestDto request
    ) {
        individualGradeCommandService.updateIndividualGrade(
                individualGradeId,
                request
        );
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    //삭제
    @DeleteMapping("/{individualGradeId}")
    public ResponseEntity<ApiResponse<Void>> deleteIndividualGrade(
            @PathVariable Long individualGradeId
    ) {
        individualGradeCommandService.deleteIndividualGrade(individualGradeId);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
    //제출
    @PostMapping("/{individualGradeId}/submit")
    public ResponseEntity<ApiResponse<Void>> submitIndividualGrade(
            @PathVariable Long individualGradeId
    ) {
        individualGradeCommandService.submitIndividualGrade(individualGradeId);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    //승인
    @PostMapping("/{individualGradeId}/approve")
    public ResponseEntity<ApiResponse<Void>> approveIndividualGrade(
            @PathVariable Long individualGradeId
    ) {
        individualGradeCommandService.approveIndividualGrade(individualGradeId);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
