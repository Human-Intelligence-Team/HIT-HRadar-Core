package org.hit.hradar.global.code.command.domain.repository;

import org.hit.hradar.global.code.command.domain.aggregate.CodeGroup;

import java.util.List;
import java.util.Optional;

public interface CodeGroupRepository {
    boolean existsByGroupCode(String groupCode);

    Optional<CodeGroup> findByGroupCode(String groupCode);

    CodeGroup save(CodeGroup codeGroup);

    List<CodeGroup> findAllActive();

    Optional<CodeGroup> findActiveByGroupCode(String groupCode);

}
