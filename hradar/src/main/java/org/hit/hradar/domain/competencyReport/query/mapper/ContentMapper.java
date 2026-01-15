package org.hit.hradar.domain.competencyReport.query.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.hit.hradar.domain.competencyReport.query.dto.ContentDTO;
import org.hit.hradar.domain.competencyReport.query.dto.request.ContentSearchRequest;

@Mapper
public interface ContentMapper {

  List<ContentDTO> findAllContents(ContentSearchRequest request);
}
