package org.hit.hradar.domain.evaluation.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.hit.hradar.domain.evaluation.query.dto.response.row.EvaluationQuestionRow;

import java.util.List;

@Mapper
public interface EvaluationQuestionMapper {

    List<EvaluationQuestionRow> findBySectionIds(
            @Param("sectionIds") List<Long> sectionIds
    );
}
