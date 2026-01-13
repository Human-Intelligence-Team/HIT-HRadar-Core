package org.hit.hradar.domain.competencyReport.command.infrastructure.repository;

import org.hit.hradar.domain.competencyReport.command.domain.aggregate.ContentTag;
import org.hit.hradar.domain.competencyReport.command.domain.repository.ContentsTagRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaContentsTagRepository extends ContentsTagRepository, JpaRepository<ContentTag, Long> {

}
