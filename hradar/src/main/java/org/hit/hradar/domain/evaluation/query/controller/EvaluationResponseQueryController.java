package org.hit.hradar.domain.evaluation.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.evaluation.query.dto.response.EvaluationResponseQueryDto;
import org.hit.hradar.domain.evaluation.query.service.EvaluationResponseQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Evaluation Response Query", description = "평가 응답 조회 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/evaluation-responses")
public class EvaluationResponseQueryController {

    private final EvaluationResponseQueryService queryService;

    @Operation(summary = "배정별 응답 목록 조회", description = "특정 평가 배정(Assignment)에 대한 전체 응답 내용을 조회합니다.")
    @GetMapping("/{assignmentId}")
    public ResponseEntity<List<EvaluationResponseQueryDto>> getResponses(
            @PathVariable Long assignmentId) {
        return ResponseEntity.ok(
                queryService.getResponses(assignmentId));
    }
}
