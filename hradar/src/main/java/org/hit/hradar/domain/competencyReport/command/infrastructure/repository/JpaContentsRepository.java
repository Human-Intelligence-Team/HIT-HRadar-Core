package org.hit.hradar.domain.competencyReport.command.infrastructure.repository;

import org.hit.hradar.domain.competencyReport.command.domain.aggregate.Contents;
import org.hit.hradar.domain.competencyReport.command.domain.repository.ContentsRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaContentsRepository extends ContentsRepository, JpaRepository<Contents, Long> {

}
