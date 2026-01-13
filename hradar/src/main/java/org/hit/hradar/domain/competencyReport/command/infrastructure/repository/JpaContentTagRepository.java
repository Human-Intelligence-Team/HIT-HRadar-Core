package org.hit.hradar.domain.competencyReport.command.infrastructure.repository;

import java.util.List;
import org.hit.hradar.domain.competencyReport.command.domain.aggregate.ContentTag;
import org.hit.hradar.domain.competencyReport.command.domain.repository.ContentTagRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaContentTagRepository extends ContentTagRepository, JpaRepository<ContentTag, Long> {
  //void saveAll(List<ContentTag> contentTags);
}
