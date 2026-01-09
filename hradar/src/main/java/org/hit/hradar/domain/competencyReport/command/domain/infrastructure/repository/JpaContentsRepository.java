package org.hit.hradar.domain.competencyReport.command.domain.infrastructure.repository;

import org.hit.hradar.domain.competencyReport.command.domain.aggreage.Contents;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaContentsRepository extends JpaRepository<Contents, Long> {

}
