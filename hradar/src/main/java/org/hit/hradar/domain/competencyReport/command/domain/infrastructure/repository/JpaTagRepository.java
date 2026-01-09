package org.hit.hradar.domain.competencyReport.command.domain.infrastructure.repository;

import org.hit.hradar.domain.competencyReport.command.domain.aggreage.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTagRepository extends JpaRepository<Tag, Long> {

}
