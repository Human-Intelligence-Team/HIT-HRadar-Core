package org.hit.hradar.domain.competencyReport.command.domain.infrastructure.repository;

import org.hit.hradar.domain.competencyReport.command.domain.aggreage.ContentTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaContentsTagRepository extends JpaRepository<ContentTag, Long> {

}
