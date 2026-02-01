package org.hit.hradar.domain.evaluation.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.hit.hradar.domain.evaluation.query.dto.response.row.CollaborationRadarRow;

import java.util.List;

@Mapper
public interface CollaborationRadarMapper {
    /**
     * 피평가자(evaluateeId)가 받은 다면평가 중
     * 섹션 제목이 '협업'인 섹션의 RATING 문항 점수를
     * 문항별로 평균낸 뒤 0~100으로 정규화하여 조회.
     */
    List<CollaborationRadarRow> selectMyCollaborationRadar(
            @Param("evaluateeId") Long evaluateeId
    );
}
