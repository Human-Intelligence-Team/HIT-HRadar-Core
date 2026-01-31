package org.hit.hradar.domain.evaluation.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.hit.hradar.domain.evaluation.query.dto.response.row.ObjectiveOptionRow;

import java.util.List;

@Mapper
public interface ObjectiveOptionMapper {


    List<ObjectiveOptionRow> findByQuestionIds(
            @Param("questionIds") List<Long> questionIds
    );
}
