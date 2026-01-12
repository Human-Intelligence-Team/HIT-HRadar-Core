package org.hit.hradar.domain.competencyReport.command.repository;


import org.hit.hradar.domain.competencyReport.command.domain.aggregate.Tag;
import org.hit.hradar.domain.competencyReport.command.domain.infrastructure.repository.TagRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaTagRepository extends TagRepository, JpaRepository<Tag, Long> {

}
