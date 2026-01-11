package org.hit.hradar.domain.competencyReport.command.repository;

import org.hit.hradar.domain.competencyReport.command.domain.aggregate.ContentTag;
import org.hit.hradar.domain.competencyReport.command.domain.infrastructure.repository.ContentsTagRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaContentsTagRepository extends ContentsTagRepository, JpaRepository<ContentTag, Long> {

}
