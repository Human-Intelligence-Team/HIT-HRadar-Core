package org.hit.hradar.global.code.command.infrastructure;

import org.hit.hradar.global.code.command.domain.aggregate.CodeGroup;
import org.hit.hradar.global.code.command.domain.repository.CodeGroupRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CodeGroupJpaRepository extends CodeGroupRepository, JpaRepository<CodeGroup, String> {

    List<CodeGroup> findByIsDeleted(Character isDeleted);

    Optional<CodeGroup> findByGroupCodeAndIsDeleted(
            String groupCode,
            Character isDeleted
    );

    // 🔹 도메인 메서드 구현 (핵심)
    @Override
    default List<CodeGroup> findAllActive() {
        return findByIsDeleted('N');
    }

    @Override
    default Optional<CodeGroup> findActiveByGroupCode(String groupCode) {
        return findByGroupCodeAndIsDeleted(groupCode, 'N');
    }
}
