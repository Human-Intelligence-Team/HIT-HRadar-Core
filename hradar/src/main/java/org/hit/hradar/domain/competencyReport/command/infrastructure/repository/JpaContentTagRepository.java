package org.hit.hradar.domain.competencyReport.command.infrastructure.repository;


import java.util.List;
import org.hit.hradar.domain.competencyReport.command.domain.aggregate.ContentTag;
import org.hit.hradar.domain.competencyReport.command.domain.repository.ContentTagRepository;
import org.hit.hradar.domain.competencyReport.query.dto.TagDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaContentTagRepository extends ContentTagRepository, JpaRepository<ContentTag, Long> {

  default void saveAllWithPolicy(List<ContentTag> contentTags) {
    if (contentTags == null || contentTags.isEmpty()) {
      return;
    }
    saveAll(contentTags);
  }

  default List<TagDTO> findAllByContentId(Long contentId) {
    if (contentId == null) {
      return null;
    }
    return findAllByContentId(contentId);
  }
}
