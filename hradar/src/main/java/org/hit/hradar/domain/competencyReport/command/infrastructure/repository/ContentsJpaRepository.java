package org.hit.hradar.domain.competencyReport.command.infrastructure.repository;

import org.hit.hradar.domain.competencyReport.command.domain.aggregate.Content;
import org.hit.hradar.domain.competencyReport.command.domain.repository.ContentRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentsJpaRepository extends ContentRepository, JpaRepository<Content, Long> {

}
