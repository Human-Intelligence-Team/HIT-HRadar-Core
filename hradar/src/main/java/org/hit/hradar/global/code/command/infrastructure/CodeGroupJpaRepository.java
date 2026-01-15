package org.hit.hradar.global.code.command.infrastructure;

import org.hit.hradar.global.code.command.domain.aggregate.CodeGroup;
import org.hit.hradar.global.code.command.domain.repository.CodeGroupRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodeGroupJpaRepository extends CodeGroupRepository, JpaRepository<CodeGroup, String> {
}
