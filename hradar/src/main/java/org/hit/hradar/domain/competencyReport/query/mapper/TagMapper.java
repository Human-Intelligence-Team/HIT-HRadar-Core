package org.hit.hradar.domain.competencyReport.query.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.hit.hradar.domain.competencyReport.query.dto.TagDTO;
import org.hit.hradar.domain.competencyReport.query.dto.request.TagSearchRequest;

@Mapper
public interface TagMapper {

  List<TagDTO> findAllTags(TagSearchRequest request);

  List<TagDTO> findAllTagsByContentId(Long id);
}
