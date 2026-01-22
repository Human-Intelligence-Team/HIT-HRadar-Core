package org.hit.hradar.domain.grading.query.controller;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.grading.query.dto.response.GradeObjectionResponseDto;
import org.hit.hradar.domain.grading.query.service.GradeObjectionQueryService;
import org.hit.hradar.global.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/grade-objections/query")
public class GradeObjectionQueryController {

    private final GradeObjectionQueryService gradeObjectionQueryService;

    //개인 등급 선택시 이의제기 조회
    @GetMapping
    public ResponseEntity<ApiResponse<List<GradeObjectionResponseDto>>> getObjections(
            @RequestParam Long individualGradeId
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        gradeObjectionQueryService.getObjectionsByIndividualGrade(
                                individualGradeId
                        )
                )
        );
    }
}
