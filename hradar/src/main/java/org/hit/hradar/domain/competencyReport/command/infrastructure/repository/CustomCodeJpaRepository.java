package org.hit.hradar.domain.competencyReport.command.infrastructure.repository;

import java.util.List;
import org.hit.hradar.domain.competencyReport.command.domain.aggregate.ContentTag;
import org.hit.hradar.domain.competencyReport.command.domain.aggregate.CustomCode;
import org.hit.hradar.domain.competencyReport.command.domain.repository.CustomCodeRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomCodeJpaRepository extends CustomCodeRepository,
    JpaRepository<CustomCode, Long> {

}
