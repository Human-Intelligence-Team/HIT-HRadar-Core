package org.hit.hradar.domain.evaluation.query.dto.response.row;

import lombok.Getter;


@Getter
public class MonthlyRejectionCountRow {

    /** YYYY-MM */
    private String month;

    /** 반려 횟수 */
    private Long count;
}
