package org.hit.hradar.domain.competencyReport.command.domain.infrastructure.repository;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTagRepository extends JpaRepository<Tag, Long> {

}
