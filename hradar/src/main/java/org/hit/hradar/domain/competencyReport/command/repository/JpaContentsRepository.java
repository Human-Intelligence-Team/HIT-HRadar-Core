package org.hit.hradar.domain.competencyReport.command.repository;

import org.hit.hradar.domain.competencyReport.command.domain.aggreage.Contents;
import org.hit.hradar.domain.competencyReport.command.domain.infrastructure.repository.ContentsRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaContentsRepository extends ContentsRepository, JpaRepository<Contents, Long> {

}
