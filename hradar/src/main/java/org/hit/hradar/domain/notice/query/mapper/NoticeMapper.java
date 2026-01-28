package org.hit.hradar.domain.notice.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.hit.hradar.domain.notice.query.dto.NoticeListItemResponse;
import org.hit.hradar.domain.notice.query.dto.NoticeSearchCondition;

import java.util.List;

@Mapper
public interface NoticeMapper {

    // Offset Paging
    List<NoticeListItemResponse> search(
            @Param("cond") NoticeSearchCondition cond,
            @Param("offset") int offset,
            @Param("size") int size
    );

    long count(@Param("cond") NoticeSearchCondition cond);
}