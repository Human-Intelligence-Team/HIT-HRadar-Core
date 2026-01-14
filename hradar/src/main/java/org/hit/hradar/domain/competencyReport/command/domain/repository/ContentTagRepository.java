package org.hit.hradar.domain.competencyReport.command.domain.repository;

import java.util.List;
import org.hit.hradar.domain.competencyReport.command.domain.aggregate.ContentTag;
import org.hit.hradar.domain.competencyReport.query.dto.TagDTO;

public interface ContentTagRepository {

  void saveAllWithPolicy(List<ContentTag> contentTags);
  void deleteAllByContentId(Long contentId);

}
