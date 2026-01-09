package org.hit.hradar.domain.competencyReport.command.repository;

import org.hit.hradar.domain.competencyReport.command.domain.aggreage.Tag;
import org.hit.hradar.domain.competencyReport.command.domain.infrastructure.repository.TagRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTagRepository extends TagRepository, JpaRepository<Tag, Long> {

}
