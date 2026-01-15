package org.hit.hradar.global.code.command.infrastructure;

import org.hit.hradar.global.code.command.domain.aggregate.CommonCode;
import org.hit.hradar.global.code.command.domain.repository.CommonCodeRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommonCodeJpaRepository  extends CommonCodeRepository, JpaRepository<CommonCode, String> {

    boolean existsByGroupCode_GroupCodeAndCodeAndIsDeleted(
            String groupCode,
            String code,
            Character isDeleted
    );

    Optional<CommonCode> findByGroupCode_GroupCodeAndCodeAndIsDeleted(
            String groupCode,
            String code,
            Character isDeleted
    );

    @Override
    default boolean existsActiveCode(String groupCode, String code) {
        return existsByGroupCode_GroupCodeAndCodeAndIsDeleted(
                groupCode, code, 'N'
        );
    }

    @Override
    default Optional<CommonCode> findActiveCode(String groupCode, String code) {
        return findByGroupCode_GroupCodeAndCodeAndIsDeleted(
                groupCode, code, 'N'
        );
    }
}
