package org.hit.hradar.domain.employee.command.application.dto.reponse;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * CSV 파일 업로드 후 미리보기 전체 응답 DTO
 */
@Getter
@Builder
public class EmployeeCsvPreviewResponse {

    private int totalCount; // 전체 행 개수
    private int validCount; // 유효한 행 개수
    private int invalidCount; // 유효하지 않은 행 개수

    private List<EmployeeCsvPreviewRow> rows; // 전체 행 데이터 리스트
}
