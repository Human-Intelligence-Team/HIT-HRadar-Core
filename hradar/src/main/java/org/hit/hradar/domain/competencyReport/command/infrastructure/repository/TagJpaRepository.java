package org.hit.hradar.domain.competencyReport.command.infrastructure.repository;


import java.util.List;
import org.hit.hradar.domain.competencyReport.command.domain.aggregate.Tag;
import org.hit.hradar.domain.competencyReport.command.domain.repository.TagRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagJpaRepository extends TagRepository, JpaRepository<Tag, Long> {

  void deleteByTagIdIn(List<Long> tagIds);
}
