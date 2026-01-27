package org.hit.hradar.domain.notice.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.hit.hradar.domain.notice.query.dto.NoticeCategoryResponse;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Mapper
public interface NoticeCategoryMapper {

    List<NoticeCategoryResponse> findByCompany(
            @Param("companyId") Long companyId
    );
}
