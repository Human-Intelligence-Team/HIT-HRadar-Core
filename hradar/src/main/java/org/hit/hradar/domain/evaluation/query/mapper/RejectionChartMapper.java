package org.hit.hradar.domain.evaluation.query.mapper;


import org.hit.hradar.domain.evaluation.query.dto.response.row.MonthlyRejectionCountRow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RejectionChartMapper {

    /**
     * 사원별 월 단위 반려 횟수 조회
     */
    List<MonthlyRejectionCountRow> selectMonthlyRejectionCount(
            @Param("empId") Long empId,
            @Param("startYm") String startYm,
            @Param("endYm") String endYm
    );
}
