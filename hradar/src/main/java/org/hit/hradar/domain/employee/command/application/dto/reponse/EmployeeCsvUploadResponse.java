package org.hit.hradar.domain.employee.command.application.dto.reponse;

import lombok.Builder;
import lombok.Getter;

/**
 * CSV 사원 등록 결과 응답
 */
@Getter
@Builder
public class EmployeeCsvUploadResponse {
    private int successCount;
    private int totalCount;
}
